package com.aws.application.errors.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Instantiates a new instance of {@link ResourceNotFoundException}.
     *
     * @param resourceName  searched resource
     * @param fieldName     type of the resource which cannot be found
     * @param fieldValue    an identifier of the resource which cannot be found
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s:'%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
