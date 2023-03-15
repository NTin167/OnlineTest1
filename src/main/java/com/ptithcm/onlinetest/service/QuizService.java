package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.BadRequestException;
import com.ptithcm.onlinetest.exception.ResourceNotFoundException;
import com.ptithcm.onlinetest.model.Category;
import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.PagedResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.repository.CategoryRepository;
import com.ptithcm.onlinetest.repository.QuizRepository;
import com.ptithcm.onlinetest.repository.UserRepository;
import com.ptithcm.onlinetest.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Transactional
@Service
public class QuizService implements IQuizService{
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public QuizResponse createQuiz(QuizRequest quizRequest) throws IOException {
        User user = userRepository.findById(quizRequest.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", quizRequest.getUserId()));
        Category category = categoryRepository.findById(quizRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", quizRequest.getCategoryId()));
        if(user == null || category == null)
            return QuizResponse.builder().message("User is not exists").status(false).quiz(null).build();
        if(quizRepository.existsByTitle(quizRequest.getTitle())) {
            return QuizResponse.builder().message("Title already exists").status(false).quiz(null).build();
        }
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
                .linkImage(quizRequest.getLinkImage())
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .user(user)
                .category(category)
                .build();
        quizRepository.save(quiz);
        return QuizResponse.builder().message("Create Quiz successfuly").status(true).quiz(quiz).build();
    }

    @Override
    public Iterable<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    @Override
    public QuizResponse updateQuiz(Long quizId, QuizRequest quizRequest) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(!quizOptional.isPresent()) {
            return QuizResponse.builder().status(true).message("Update quiz successfully").quiz(quizOptional.get()).build();
        }
        Quiz quiz = quizOptional.get();
        quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
                .linkImage(quizRequest.getLinkImage())
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .build();
        if(quizRequest.getUserId() != null) {
            Optional<User> userOptional = userRepository.findById(quizRequest.getUserId());
            if(userOptional.isPresent()) {
                quiz.setUser(userOptional.get());
            }
        }

        if(quizRequest.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById((quizRequest.getCategoryId()));
            if(categoryOptional.isPresent()) {
                quiz.setCategory(categoryOptional.get());
            }
        }
        quizRepository.save(quiz);
        return QuizResponse.builder().status(true).message("Update quiz successfully").quiz(quiz).build();
    }


    @Override
    public QuizResponse deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()) {
            quizRepository.deleteById(id);
            return QuizResponse.builder().status(true).message("Deleted quiz successfully").quiz(quiz.get()).build();
        }
        return QuizResponse.builder().status(false).message("Quiz id is not exist").quiz(null).build();
    }

    @Override
    public ApiResponse createQuizx(QuizRequest quizRequest) {
        User user = userRepository.findById(quizRequest.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", quizRequest.getUserId()));
        Category category = categoryRepository.findById(quizRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", quizRequest.getCategoryId()));
        if(user == null || category == null)
            return ApiResponse.builder().message("User is not exists").status(200).data(null).build();
        if(quizRepository.existsByTitle(quizRequest.getTitle())) {
            return ApiResponse.builder().message("Title already exists").status(200).data(null).build();
        }
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
                .linkImage(quizRequest.getLinkImage())
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .user(user)
                .category(category)
                .build();
        quizRepository.save(quiz);
        return ApiResponse.builder().message("Create Quiz successfuly").status(200).data(quiz).build();
    }

    @Override
    public PagedResponse<Quiz> getAllQuizzes(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC ,"createdAt");
        Page<Quiz> quizzes = quizRepository.findAll(pageable);
        QuizResponse quizResponse;
        if(quizzes.getTotalElements() == 0 ) {
            return new PagedResponse<>(Collections.emptyList(), quizzes.getNumber(), quizzes.getSize(),
                    quizzes.getTotalElements(), quizzes.getTotalPages(), quizzes.isLast());
        }
        return new PagedResponse<>(quizzes.getContent(), quizzes.getNumber(), quizzes.getSize(),
                quizzes.getTotalElements(), quizzes.getTotalPages(), quizzes.isLast());

    }
}
