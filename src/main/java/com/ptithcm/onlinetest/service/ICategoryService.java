package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Category;
import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.request.CategoryRequest;
import com.ptithcm.onlinetest.payload.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    Iterable<Category> getAllCategory();
    Category createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    CategoryResponse deleteCategory(Long categoryId);

    List<Quiz> getQuizzByCategory(String title);
}
