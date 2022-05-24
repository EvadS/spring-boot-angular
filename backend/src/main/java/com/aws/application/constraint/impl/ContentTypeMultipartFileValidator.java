package com.aws.application.constraint.impl;

import com.aws.application.constraint.ContentType;
import com.aws.application.errors.exception.IncorrectFileException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Validator of content type. This is simple and not complete implementation
 * of content type validating. It's based just on <code>String</code> equalsIgnoreCase
 * method.
 *
 * @author SE
 */
public class ContentTypeMultipartFileValidator implements ConstraintValidator<ContentType, MultipartFile> {

    //"JPG", "JPEG", "PNG", "BMP"
    private String[] acceptedContentTypes;

    @Override
    public void initialize(ContentType constraintAnnotation) {
        this.acceptedContentTypes = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty())
            return true;

        String contentType = getContentType(value);
        String subType = org.springframework.http.MediaType.valueOf(contentType).getSubtype();

        return ContentTypeMultipartFileValidator.acceptContentType(subType, acceptedContentTypes);
    }

    private static boolean acceptContentType(String contentType, String[] acceptedContentTypes) {
        for (String accept : acceptedContentTypes) {
            // this should be done more clever to accept all possible content types
            if (contentType.equalsIgnoreCase(accept)) {
                return true;
            }
        }

        return false;
    }

    private String getContentType(MultipartFile file) {
        Tika tika = new Tika();
        try {
            return tika.detect(file.getBytes());
        } catch (IOException e) {
            throw new IncorrectFileException(String.format("Can't get mime type from file '%s'", file.getOriginalFilename()));
        }
    }
}
