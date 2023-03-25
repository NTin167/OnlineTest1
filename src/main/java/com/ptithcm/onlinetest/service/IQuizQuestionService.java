package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.model.QuizAnswer;
import com.ptithcm.onlinetest.model.QuizQuestion;
import com.ptithcm.onlinetest.payload.dto.QuizQuestionDto;

import java.util.Set;

public interface IQuizQuestionService {
    public QuizQuestion addQuizQuestion(QuizQuestion quizQuestion);

    public QuizQuestion updateQuizQuestion(QuizQuestion quizQuestion, Long id);

    public QuizQuestion getQuizQuestion(Long questionId);

    public Set<QuizQuestion> getQuizQuestions();

    public Set<QuizQuestion> getQuizQuestionsOfQuiz(Quiz quiz);

    public Set<QuizQuestionDto> getQuestionAndAnswerOfQuiz(Long quizId);

    public Iterable<QuizAnswer> getQuizAnswerByQuizQuestionId(Long questionId);
}
