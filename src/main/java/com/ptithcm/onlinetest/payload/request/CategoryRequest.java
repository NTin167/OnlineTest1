package com.ptithcm.onlinetest.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String title;

    private String description;

}
