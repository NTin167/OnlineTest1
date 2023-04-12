package com.ptithcm.onlinetest.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {
    private String title;

    private String metaTitle;

    private MultipartFile linkImage;

    private String summary;

    private int type;

    private int score;

    private int published;

    private Instant publishedAt;

    private Instant startsAt;

    private Instant endsAt;

    private String content;

    private Long categoryId;
}
