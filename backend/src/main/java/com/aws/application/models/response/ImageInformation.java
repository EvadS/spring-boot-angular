package com.aws.application.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInformation {
    private ImageResponse imageResponse;
    private List<RecognitionResponse> rekognition;
}
