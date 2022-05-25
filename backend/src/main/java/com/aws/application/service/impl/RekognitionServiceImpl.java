package com.aws.application.service.impl;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.aws.application.models.payload.RecognitionLabels;
import com.aws.application.service.RekognitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RekognitionServiceImpl implements RekognitionService {

    private final AmazonRekognition rekognitionClient;

    @Override
    public List<RecognitionLabels> searchLabels(MultipartFile multipartFile) {
        try {

            Image image = new Image().withBytes(ByteBuffer.wrap(multipartFile.getBytes()));

            DetectLabelsRequest request = new DetectLabelsRequest();
            request.setImage(image);
            request.withMaxLabels(10);
            request.withMinConfidence(90F);

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            return labels.stream()
                    .map(i -> new RecognitionLabels(i.getName(), i.getConfidence()))
                    .collect(Collectors.toList());

            //TODO: implement catch
        } catch (AmazonRekognitionException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }
}
