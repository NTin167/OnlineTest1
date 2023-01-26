package com.ptithcm.onlinetest.util;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class GenericResponse {
    private String message;

    private String error;


    public GenericResponse() {
    }

    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    public GenericResponse(List<ObjectError> allErrors, String error) {
        this.error = error;
        String temp = allErrors.stream().map(objectError -> {
            if(objectError instanceof FieldError) {
                return "{\"field\":\"" + ((FieldError) objectError).getField() + "\",\"defaultMessage\":\"" + objectError.getDefaultMessage() + "\"}";
            }
            else {
                return "{\"object\":\"" + objectError.getObjectName() + "\",\"defaultMessage\":\"" + objectError.getDefaultMessage() + "\"}";
            }
        }).collect(Collectors.joining(","));
        this.message = "[" + temp + "]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
