package com.ptithcm.onlinetest.controller;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.PagedResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @GetMapping("/{page}/{size}")
    public PagedResponse<Quiz> getAllQuizzes(@PathVariable("page") int page,
                                             @PathVariable("size") int size) {
            return quizService.getAllQuizzes(page, size);
    }

//    @GetMapping("")
//    public Iterable<Quiz> getAll() {
//        return quizService.getAllQuiz();
//    }

    @PostMapping()
    public QuizResponse createQuiz(@RequestBody @Valid QuizRequest quizRequest) throws IOException {
        return quizService.createQuiz(quizRequest);
    }

    @PutMapping("/edit/{quizId}")
    public QuizResponse updateQuiz(@RequestBody @Valid QuizRequest quizRequest ,@PathVariable("quizId") Long quizId) {
        return quizService.updateQuiz(quizId, quizRequest);
    }
    @DeleteMapping("delete/{quizId}")
    public QuizResponse deleteQuiz(@PathVariable("id") Long id) {
        return quizService.deleteQuiz(id);
    }
}
