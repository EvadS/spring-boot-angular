package com.aws.application.service.impl;

import com.aws.application.config.BucketName;
import com.aws.application.domain.Image;
import com.aws.application.errors.exception.FileStorageException;
import com.aws.application.errors.exception.ImageProcessingException;
import com.aws.application.errors.exception.ResourceNotFoundException;
import com.aws.application.mapper.ImageMapper;
import com.aws.application.models.payload.ConvertToMultipartFile;
import com.aws.application.models.payload.RecognitionLabels;
import com.aws.application.models.payload.ResizeModel;
import com.aws.application.models.response.ImageResponse;
import com.aws.application.repository.ImageRepository;
import com.aws.application.service.FileStore;
import com.aws.application.service.ImageService;
import com.aws.application.service.RekognitionService;
import com.aws.application.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.aws.application.util.Constants.DEFAULT_FILE_EXTENSION;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final FileStore fileStore;
    private final ImageRepository repository;
    private final RekognitionService rekognitionService;

    @Value("${image.preview.height:150}")
    private int previewHeight;

    @Value("${image.preview.weight:150}")
    private int previewWidth;

    @Override
    public ImageResponse saveImage(String title, MultipartFile  file) {
        log.info("save image, file:{}, title:{}", file.getOriginalFilename(), title);
        String fileName = String.format("%s", file.getOriginalFilename());

        try {
            String uuidName = UUID.randomUUID().toString();
            fileStore.uploadFile(BucketName.IMAGE_BUCKET_NAME.getBucketName(), uuidName, ImageUtils.ConvertMultiPartToFile(file));

            String fileUrl = fileStore.getUrl(BucketName.IMAGE_BUCKET_NAME.getBucketName(), uuidName);
            log.info("Uploaded to s3: {}", fileUrl);
            String previewFileName = ImageUtils.BuildPreviewImageName(uuidName);
            MultipartFile previewMultipart = scaleImage(file, previewFileName, DEFAULT_FILE_EXTENSION);

            // upload preview
            fileStore.uploadFile(BucketName.IMAGE_BUCKET_NAME.getBucketName(), previewFileName, ImageUtils.ConvertMultiPartToFile(previewMultipart));
            String reviewFileUrl = fileStore.getUrl(BucketName.IMAGE_BUCKET_NAME.getBucketName(), previewFileName);


            List<RecognitionLabels> recognitionLabels = rekognitionService.searchLabels(file);
            recognitionLabels.stream().forEach(i -> log.info(i.toString()));

            Image image = Image.builder()
                    .title(title)
                    .imagePath(fileUrl)
                    .imageFileName(fileName)
                    .previewImagePath(reviewFileUrl)
                    .build();
            repository.save(image);

            log.debug("preview image saved, id:{}", image.getId());
            return ImageMapper.INSTANCE.toImageResponse(image);

        } catch (IOException e) {
            throw new FileStorageException(e.getMessage());

        }
    }


    @Override
    public byte[] downloadImage(Long id) {
        Image image = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Image", "id", id));
        return fileStore.download(image.getImagePath(), image.getImageFileName());
    }

    @Override
    public List<ImageResponse> searchByName(String filter) {
        log.info("Search by title, filter:{}", filter);
        if (StringUtils.isEmpty(filter)) {
            return repository.findAll().stream()
                    .map(ImageMapper.INSTANCE::toImageResponse)
                    .collect(Collectors.toList());
        } else {

            return repository.findByTitleContains(filter).stream()
                    .map(ImageMapper.INSTANCE::toImageResponse)
                    .collect(Collectors.toList());
        }
    }

    private MultipartFile scaleImage(MultipartFile multipartFile, String fileName, String format) {

        try {
            BufferedImage previewBufferedImage = ImageIO.read(multipartFile.getInputStream());

            int originalWidth = previewBufferedImage.getWidth();
            int originalHeight = previewBufferedImage.getHeight();
            log.debug("scale image, original weight:{}, height:{}", originalWidth, originalHeight);

            ResizeModel resizeModel = ImageUtils.BuildResizeModelByCurrentSize(originalWidth,
                    originalHeight, previewWidth, previewHeight);

            log.debug("started scaling image, resize to:{}", resizeModel);
            BufferedImage croppedBufferedImage = ImageUtils.CropImage(previewBufferedImage, resizeModel);

            //BufferedImage  Convert to  ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(croppedBufferedImage, format, out);
            //ByteArrayOutputStream  Convert to  byte[]
            byte[] imageByte = out.toByteArray();
            // Will  byte[]  Convert to  MultipartFile
            return new ConvertToMultipartFile(imageByte, fileName, multipartFile.getOriginalFilename(),
                    format, imageByte.length);
        } catch (Exception e) {
            log.error("some error" + e.getMessage());
            throw new ImageProcessingException("some error" + e.getMessage());
        }
    }
}
