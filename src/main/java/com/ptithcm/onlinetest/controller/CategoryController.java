package com.ptithcm.onlinetest.controller;

import com.ptithcm.onlinetest.model.Category;
import com.ptithcm.onlinetest.payload.request.CategoryRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.CategoryResponse;
import com.ptithcm.onlinetest.security.CurrentUser;
import com.ptithcm.onlinetest.security.UserPrincipal;
import com.ptithcm.onlinetest.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/getAll")
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/getQuizzes/{categoryId}")
    public Iterable<?> getQuizzesByCategory(@PathVariable(value = "categoryId") Long id) {

        return categoryService.getQuizzByCategory(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryRequest categoryRequest, @CurrentUser UserPrincipal userPrincipal) {
        Category category = categoryService.createCategory(categoryRequest, userPrincipal);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{categoryId}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location)
                .body(ApiResponse.builder().status(200).data(category).message("Created category successfully").build());
    }

    @PutMapping(value = "/edit/{categoryId}")
    public CategoryResponse updateCategory(@RequestBody @Valid CategoryRequest categoryRequest,
                                           @PathVariable(value = "categoryId") Long categoryId,
                                           @CurrentUser UserPrincipal userPrincipal) {
        return categoryService.updateCategory(categoryId, categoryRequest, userPrincipal);
    }

    @DeleteMapping(value = "/delete")
    public CategoryResponse deleteCategory(@RequestParam Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}