package com.ptithcm.onlinetest.payload.response;

import com.ptithcm.onlinetest.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String message;

    private boolean status;

    private Category category;
}
