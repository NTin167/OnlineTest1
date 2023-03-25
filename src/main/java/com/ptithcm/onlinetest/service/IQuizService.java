package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.dto.QuestionTakeDto;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.PagedResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.security.UserPrincipal;

import java.io.IOException;

public interface IQuizService {

    QuizResponse createQuiz(QuizRequest quizRequest) throws IOException;

    Iterable<Quiz> getAllQuiz();

    QuizResponse updateQuiz(Long id, QuizRequest quizRequest) throws IOException;

    QuizResponse deleteQuiz(Long id);

    ApiResponse createQuizx(QuizRequest quizRequest);

    PagedResponse<Quiz> getAllQuizzes(int page, int size);

    QuestionTakeDto takeQuestion(UserPrincipal userPrincipal, Long quizId);

}
