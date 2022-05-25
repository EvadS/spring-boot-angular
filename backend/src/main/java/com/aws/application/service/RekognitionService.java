package com.aws.application.service;


import com.amazonaws.services.rekognition.model.Image;
import com.aws.application.models.payload.RecognitionLabels;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface RekognitionService {
    List<RecognitionLabels> searchLabels(MultipartFile file);
}
