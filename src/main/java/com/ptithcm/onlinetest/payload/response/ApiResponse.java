package com.ptithcm.onlinetest.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.Builder.Default;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse <T> {
    @Default
    private int status = 200;

    @Default
    private int error = 0;

    @Default
    private String message = null;

    private T data;

    public ApiResponse(int status) {
        this.status = status;
    }

    public ApiResponse(int status, int error) {
        this.status = status;
        this.error = error;
    }

    public ApiResponse(T data) {
        this.data = data;
        this.status = 200;
    }

    public static <T> ApiResponse<T> of(T data) {
        ApiResponse apiResponse = new ApiResponse<T>();
        apiResponse.setData(data);
        return apiResponse;
    }

    public static ApiResponse fromErrorCode(CodeStatus codeStatus) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(codeStatus.getCode());
        apiResponse.setStatus(codeStatus.getStatus().value());
        return apiResponse;
    }
}
