package com.aws.application.errors.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ErrorDetail {

    private String message;

    private String detail;

    private Integer status;

    private List<ApiValidationError> errors;

    private String stackTrace;

    public ErrorDetail(final String title, final String detail) {
        this.message = title;
        this.detail = detail;
    }
}
