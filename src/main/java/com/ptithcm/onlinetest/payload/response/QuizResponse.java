package com.ptithcm.onlinetest.payload.response;

import com.ptithcm.onlinetest.model.Quiz;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuizResponse {
    private boolean status;
    private String message;
    private Quiz quiz;
}
