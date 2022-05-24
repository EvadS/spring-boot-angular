package com.aws.application.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectFileException extends RuntimeException {
    public IncorrectFileException(String s) {
        super(s);
    }
}
