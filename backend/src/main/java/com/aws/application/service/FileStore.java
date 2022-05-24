package com.aws.application.service;

import java.io.File;

public interface FileStore {
    void uploadFile(String bucketName, String fileName, File file);

    byte[] download(String path, String key);

    String getUrl(String bucketName, String name);
}
