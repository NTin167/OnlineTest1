package com.ptithcm.onlinetest.repository;

import com.ptithcm.onlinetest.model.QuizAnswer;
import com.ptithcm.onlinetest.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
    Iterable<QuizAnswer> findQuizAnswerByQuizQuestion(QuizQuestion quizQuestion);
    Iterable<QuizAnswer> findAllByQuizQuestionId(Long quizQuestionId);
    @Query("SELECT q.id, q.content, q.correct, q.quizQuestion.id FROM QuizAnswer q WHERE q.correct = 1")
    Iterable<?> getQuizAnswerTrue();

}
