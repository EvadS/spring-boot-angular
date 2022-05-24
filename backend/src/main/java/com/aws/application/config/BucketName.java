package com.aws.application.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    IMAGE_BUCKET_NAME("aws-app-bucket");
    private final String bucketName;
}