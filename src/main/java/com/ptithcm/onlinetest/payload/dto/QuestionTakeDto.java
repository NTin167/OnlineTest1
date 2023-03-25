package com.ptithcm.onlinetest.payload.dto;

import com.ptithcm.onlinetest.model.Take;

import java.util.Set;

public class QuestionTakeDto {
    private Set<QuizQuestionDto> questions;
    private Take take;

    public QuestionTakeDto() {
    }

    public QuestionTakeDto(Set<QuizQuestionDto> quizQuestionDto, Take take) {
        this.questions = quizQuestionDto;
        this.take = take;
    }

    public Set<QuizQuestionDto> getQuizQuestionDto() {
        return questions;
    }

    public void setQuizQuestionDto(Set<QuizQuestionDto> quizQuestionDto) {
        this.questions = quizQuestionDto;
    }

    public Take getTake() {
        return take;
    }

    public void setTake(Take take) {
        this.take = take;
    }
}
