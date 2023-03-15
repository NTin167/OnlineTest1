package com.ptithcm.onlinetest.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class QuizRequest {
    private String title;

    private String metaTitle;

    private String linkImage;

    private String summary;

    private int type;

    private int score;

    private int published;

    private Instant publishedAt;

    private Instant startsAt;

    private Instant endsAt;

    private String content;

    private Long userId;

    private Long categoryId;
}
