package com.ptithcm.onlinetest.controller;

import com.ptithcm.onlinetest.model.Quiz;
import com.ptithcm.onlinetest.payload.dto.QuestionTakeDto;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.PagedResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.security.CurrentUser;
import com.ptithcm.onlinetest.security.UserPrincipal;
import com.ptithcm.onlinetest.service.QuizQuestionService;
import com.ptithcm.onlinetest.service.QuizService;
import com.ptithcm.onlinetest.service.TakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @Autowired
    QuizQuestionService quizQuestionService;

    @Autowired
    TakeService takeService;



    @GetMapping("/{page}/{size}")
    public PagedResponse<Quiz> getAllQuizzes(@PathVariable("page") int page,
                                             @PathVariable("size") int size) {
            return quizService.getAllQuizzes(page, size);
    }


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

    @PostMapping("/{quizId}")
    public QuestionTakeDto takeQuiz(@CurrentUser UserPrincipal userPrincipal,  @PathVariable("quizId") Long quizId)
    {
        return quizService.takeQuestion(userPrincipal, quizId);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return quizService.tinhDiem();
    }

}
