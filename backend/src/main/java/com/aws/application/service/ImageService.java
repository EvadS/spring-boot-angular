package com.aws.application.service;

import com.aws.application.models.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageService {
    ImageResponse saveImage(String title, MultipartFile file);

    byte[] downloadImage(Long id);

    List<ImageResponse> searchByName(String filter);
}
