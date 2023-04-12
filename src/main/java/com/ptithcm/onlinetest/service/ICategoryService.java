package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Category;
import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.request.CategoryRequest;
import com.ptithcm.onlinetest.payload.response.CategoryResponse;
import com.ptithcm.onlinetest.security.UserPrincipal;

public interface ICategoryService {
    Iterable<Category> getAllCategory();
    Category createCategory(CategoryRequest categoryRequest, UserPrincipal userPrincipal);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest, UserPrincipal userPrincipal);
    CategoryResponse deleteCategory(Long categoryId);

    Iterable<Quiz> getQuizzByCategory(Long id);
}
