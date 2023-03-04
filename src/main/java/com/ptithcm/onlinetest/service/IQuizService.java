package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;

import java.io.IOException;

public interface IQuizService {

    QuizResponse createQuiz(QuizRequest quizRequest) throws IOException;

    Iterable<Quiz> getAllQuiz();

    QuizResponse updateQuiz(Long id, QuizRequest quizRequest) throws IOException;

    ApiResponse<?> deleteQuiz(Long id);

}
