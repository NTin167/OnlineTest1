package com.ptithcm.onlinetest.payload.dto;

import lombok.Data;

@Data
public class QuizAnswerDto {
    private Long id;

    private int active;

    private String content;
}
