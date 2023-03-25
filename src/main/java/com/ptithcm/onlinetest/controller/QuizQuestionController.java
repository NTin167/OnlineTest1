package com.ptithcm.onlinetest.controller;

import com.ptithcm.onlinetest.model.QuizQuestion;
import com.ptithcm.onlinetest.repository.QuizAnswerRepository;
import com.ptithcm.onlinetest.repository.QuizQuestionRepository;
import com.ptithcm.onlinetest.repository.QuizRepository;
import com.ptithcm.onlinetest.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuizQuestionController {
    @Autowired
    QuizQuestionService quizQuestionService;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    QuizAnswerRepository quizAnswerRepository;


    @GetMapping("quizAnswers/{qaId}")
    public ResponseEntity<?> getQuestionAnswersByQuestion(@PathVariable("qaId") Long qaId) {
        QuizQuestion quizQuestion = quizQuestionRepository.findById(qaId).get();
        return ResponseEntity.ok(quizAnswerRepository.findQuizAnswerByQuizQuestion(quizQuestion));
    }


    @GetMapping("/quizzes/{qid}")
    public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) {
        return ResponseEntity.ok(quizQuestionService.getQuestionAndAnswerOfQuiz(qid));
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody QuizQuestion quizQuestion) {
        return ResponseEntity.ok(quizQuestionService.addQuizQuestion(quizQuestion));
    }


}
