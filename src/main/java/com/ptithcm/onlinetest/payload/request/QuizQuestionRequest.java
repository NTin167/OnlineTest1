package com.ptithcm.onlinetest.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizQuestionRequest {
    private String type;

    private int active;

    private int level;

    private int score;

    private String content;

    private String quiz;
}
