package com.aws.application.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class RecognitionResponse {
    private String name;
    private Float confidence;
}