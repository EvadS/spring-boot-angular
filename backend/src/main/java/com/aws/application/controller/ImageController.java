package com.aws.application.controller;


import com.aws.application.config.RestConfig;
import com.aws.application.constraint.ContentType;
import com.aws.application.models.response.ImageResponse;
import com.aws.application.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(RestConfig.IMAGE_CONTROLLER_API)
@AllArgsConstructor
@CrossOrigin("*")
@Validated
public class ImageController {
    private final ImageService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam(RestConfig.TITLE_REQUEST_PARAM ) String title,
                                                     @Valid @ContentType({"JPG", "JPEG", "PNG", "BMP"})
                                                     @RequestParam(RestConfig.FILE_REQUEST_PARAM) MultipartFile file)  {
        ImageResponse imageResponse = service.saveImage(title, file);
        return ResponseEntity.ok(imageResponse);
    }

    @RequestMapping(value = RestConfig.SEARCH_API , method = RequestMethod.GET)
    public ResponseEntity<List<ImageResponse>> getArticles(
            @RequestParam(required = false, defaultValue = "") String filter) {

        List<ImageResponse> searchResponse = service.searchByName(filter);
        return ResponseEntity.ok(searchResponse);
    }

    @GetMapping(value = RestConfig.DOWNLOAD_IMAGE_API)
    public byte[] downloadTodoImage(@PathVariable("id") Long id) {
        return service.downloadImage(id);
    }
}
