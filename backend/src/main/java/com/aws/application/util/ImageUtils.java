package com.aws.application.util;

import com.aws.application.models.payload.ResizeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class ImageUtils {

    public static File ConvertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static BufferedImage CropImage(BufferedImage img, ResizeModel resizeModel) {
        log.info("Crop image, resize model:{}", resizeModel);
        // change size
        BufferedImage resized = ResizeBufferedImage(img, resizeModel.getPreviewWidth(), resizeModel.getPreviewHeight());

        // Crop
        return resized.getSubimage(
                resizeModel.getDx(),
                resizeModel.getDy(),
                resizeModel.getCroppedWidth(),
                resizeModel.getCroppedHeight()); // height
    }

    public static BufferedImage ResizeBufferedImage(BufferedImage img, int width, int height) {
        log.info("resize Buffered image");
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        log.info("image resized");

        return resized;
    }

    public static ResizeModel BuildResizeModelByCurrentSize(int originalWidth, int originalHeight, int destinationWidth, int recommendedHeight) {
        ResizeModel model;
        Dimension scalableSize = GetDimensionsByFixedWidth(originalWidth, originalHeight, destinationWidth);
        log.info("build resize model by scalable size: {}", scalableSize);
        int dx = 0;
        int dy = 0;

        int preview_width = scalableSize.width;
        int preview_height = scalableSize.height;

        // landscape
        if (scalableSize.width > destinationWidth) {
            int diffWidth = scalableSize.width - destinationWidth;
            dx = diffWidth / 2;
        }

        // portrait
        if (scalableSize.height > recommendedHeight) {
            int diffHeight = scalableSize.height - recommendedHeight;
            dy = diffHeight / 2;

            model = new ResizeModel(dx, dy, preview_width, preview_height, destinationWidth, recommendedHeight);
        } else if (recommendedHeight > scalableSize.height) {
            preview_height = scalableSize.height;

            model = new ResizeModel(dx, dy, preview_width, preview_height, destinationWidth, preview_height);
        } else {
            // w == h
            model = new ResizeModel(dx, dy, preview_width, preview_height, destinationWidth, recommendedHeight);
        }

        log.info("Calculate resize model:{}", model);
        return model;
    }

    public static Dimension GetDimensionsByFixedWidth(int currWidth, int currHeight, int width) {
        float destWidth = currWidth;
        float destHeigth = currHeight;

        float ratio = width / destWidth;

        destWidth = width;
        destHeigth = currHeight * ratio;

        return new Dimension((int) destWidth, (int) destHeigth);
    }

    public static String BuildPreviewImageName(String fileName) {
        return Constants.PREVIEW_PREFFIX + fileName;
    }
}
