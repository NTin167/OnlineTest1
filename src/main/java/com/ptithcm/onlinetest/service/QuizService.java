package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.model.User;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.repository.QuizRepository;
import com.ptithcm.onlinetest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Transactional
@Service
public class QuizService implements IQuizService{
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public QuizResponse createQuiz(QuizRequest quizRequest) throws IOException {
        User user = userRepository.findById(quizRequest.getUserId()).get();
        if(user == null)
            return QuizResponse.builder().message("User is not exists").status(false).build();
        if(quizRepository.existsByTitle(quizRequest.getTitle())) {
            return QuizResponse.builder().message("Title already exists").status(false).build();
        }

        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
                .slug(quizRequest.getSlug())
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .user(user)
                .build();
        quizRepository.save(quiz);
        return QuizResponse.builder().message("Create Quiz successfuly").status(true).quiz(quiz).build();
    }

    @Override
    public Iterable<Quiz> getAllQuiz() {
        return null;
    }

    @Override
    public QuizResponse updateQuiz(Long quizId, QuizRequest quizRequest) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);


        if(!quiz.isPresent())
            return QuizResponse.builder().status(false).message("Cannot find Quiz").build();
        if(quizRequest.getTitle() != null) {
            quiz.get().setTitle(quizRequest.getTitle());
        }

        if(quizRequest.getMetaTitle() != null) {
            quiz.get().setMetaTitle(quizRequest.getMetaTitle());
        }

        quizRepository.save(quiz.get());
        return QuizResponse.builder().status(true).message("Update quiz successfully").quiz(quiz.get()).build();
    }


    @Override
    public ApiResponse<?> deleteQuiz(Long id) {
        return null;
    }



}
