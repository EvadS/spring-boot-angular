package com.aws.application.mapper;

import com.aws.application.domain.Image;
import com.aws.application.models.response.ImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper
public interface ImageMapper {


    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "imageFileName", target = "imageFileName"),
            @Mapping(source = "imagePath", target = "imagePath"),
            @Mapping(source = "previewImagePath", target = "previewImagePath"),
            @Mapping(source = "title", target = "title"), })
    ImageResponse toImageResponse(Image image);

}
