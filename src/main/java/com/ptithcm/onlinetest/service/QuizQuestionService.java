package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.ResourceNotFoundException;
import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.model.QuizAnswer;
import com.ptithcm.onlinetest.model.QuizQuestion;
import com.ptithcm.onlinetest.payload.dto.QuizQuestionDto;
import com.ptithcm.onlinetest.repository.QuizAnswerRepository;
import com.ptithcm.onlinetest.repository.QuizQuestionRepository;
import com.ptithcm.onlinetest.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class QuizQuestionService implements IQuizQuestionService{

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    QuizAnswerRepository quizAnswerRepository;

    @Autowired
    ModelMapper mapper;


    @Override
    public QuizQuestion addQuizQuestion(QuizQuestion quizQuestion) {
        return quizQuestionRepository.save(quizQuestion);
    }

    @Override
    public QuizQuestion updateQuizQuestion(QuizQuestion quizQuestion, Long id) {
        return null;
    }

    @Override
    public QuizQuestion getQuizQuestion(Long questionId) {
        return quizQuestionRepository.findById(questionId).get();
    }

    @Override
    public Set<QuizQuestion> getQuizQuestions() {
        return new HashSet<>(this.quizQuestionRepository.findAll());
    }

    @Override
    public Set<QuizQuestion> getQuizQuestionsOfQuiz(Quiz quiz) {
        return quizQuestionRepository.findByQuiz(quiz);
    }

    @Override
    public Set<QuizQuestionDto> getQuestionAndAnswerOfQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).get();
        if(quiz == null) {
            return null;
        }
        Set<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuiz(quiz);
        Set<QuizQuestionDto> quizDtos = new HashSet<>(quizQuestions.size());
        for(QuizQuestion quiz1:quizQuestions) {
            quizDtos.add(mapper.map(quiz1, QuizQuestionDto.class));
        }
        return quizDtos;
    }

    @Override
    public Iterable<QuizAnswer> getQuizAnswerByQuizQuestionId(Long questionId) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(questionId).orElseThrow(()-> new ResourceNotFoundException("Questin", "questionId", questionId));
        return quizAnswerRepository.findQuizAnswerByQuizQuestion(quizQuestion);
    }
}
