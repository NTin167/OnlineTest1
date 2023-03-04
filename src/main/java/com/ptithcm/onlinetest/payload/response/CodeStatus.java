package com.ptithcm.onlinetest.payload.response;

import org.springframework.http.HttpStatus;

public enum CodeStatus {
    UNAUTHORIZED(0, HttpStatus.UNAUTHORIZED),
    NOT_FOUND(404, HttpStatus.NOT_FOUND),
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR)
    ;
    private int code;
    private HttpStatus status;
    private String message;

    CodeStatus(int code, HttpStatus internalServerError) {
        this.code = code;
        this.status = internalServerError;
    }

    CodeStatus(int code, HttpStatus internalServerError, String message) {
        this.code = code;
        this.status = internalServerError;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
