package com.aws.application.errors.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectImageException extends RuntimeException {

    private final String message;

    public IncorrectImageException(String message) {
        super(message);
        this.message = message;
    }
}
