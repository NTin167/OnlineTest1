package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.AppException;
import com.ptithcm.onlinetest.exception.BadRequestException;
import com.ptithcm.onlinetest.exception.ResourceNotFoundException;
import com.ptithcm.onlinetest.model.Category;
import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.payload.request.CategoryRequest;
import com.ptithcm.onlinetest.payload.response.CategoryResponse;
import com.ptithcm.onlinetest.repository.CategoryRepository;
import com.ptithcm.onlinetest.repository.QuizRepository;
import com.ptithcm.onlinetest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Transactional
@Service
public class CategoryService implements ICategoryService{
//    @Autowired
//    CategoryRepository categoryRepository;

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;



    public Iterable<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id" + categoryId));
    }
    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByTitle(categoryRequest.getTitle()))
            throw new BadRequestException("Sorry! This Category has already Title");
        Category newCategory = Category.builder().title(categoryRequest.getTitle()).description(categoryRequest.getDescription()).build();

        Optional<User> user = userRepository.findById(categoryRequest.getUserId());
        if(user.isPresent()) {
            newCategory.setUser(user.get());
        }
        else
        {
            throw new ResourceNotFoundException("user", "userId", user);
        }
        return categoryRepository.save(newCategory);
    }
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if(!category.isPresent()) {
            return CategoryResponse.builder().status(false).message("Category id is not exist").build();
        }
        if(categoryRequest.getTitle() != null) {
            category.get().setTitle(categoryRequest.getTitle());
        }
        if(categoryRequest.getDescription() != null) {
            category.get().setDescription(categoryRequest.getDescription());
        }
        Optional<User> user = userRepository.findById(categoryRequest.getUserId());
        if(categoryRequest.getUserId()!= null && user.isPresent()) {
            category.get().setUser(user.get());
        }
        else
            throw new AppException("User id not found");
        categoryRepository.save(category.get());

        return CategoryResponse.builder().status(true).message("Update category Successfully").category(category.get()).build();
    }
    @Override
    @Transactional
    public CategoryResponse deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            if(quizRepository.existsByCategory_Id(categoryId))
                return CategoryResponse.builder().status(false).message("Category has already Quiz").build();
            categoryRepository.delete(category.get());
            return CategoryResponse.builder().status(true).message("Deleted category successfully").build();
        } else {
            return CategoryResponse.builder().status(false).message("Category is not exist").build();
        }
    }
    @Override
    public Iterable<Quiz> getQuizzByCategory(Long id) {
        return quizRepository.findAllByCategoryId(id);
    }
}
