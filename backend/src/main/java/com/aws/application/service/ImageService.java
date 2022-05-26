package com.aws.application.service;

import com.aws.application.domain.Image;
import com.aws.application.models.response.ImageInformation;
import com.aws.application.models.response.ImageResponse;
import com.aws.application.models.specification.SearchQuery;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageService {
    ImageResponse saveImage(String title, MultipartFile file);

    byte[] downloadImage(Long id);

    List<ImageResponse> searchByName(String filter);

    List<Image> search(SearchQuery searchQuery);

    ImageInformation getById(Long id);
}
