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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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


//    @PostMapping()
//    public QuizResponse createQuiz(@RequestBody @Valid QuizRequest quizRequest,
//                                   @CurrentUser UserPrincipal userPrincipal,
//                                   @RequestPart("image")FilePart filePart) throws IOException {
//        return quizService.createQuiz(quizRequest, userPrincipal, filePart);
//    }
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QuizResponse createQuiz(@RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "summary", required = false) String summary,
                                   @RequestParam(value = "type", required = false) Integer type,
                                   @RequestParam(value = "score", required = false) Integer score,
                                   @RequestParam(value = "published", required = false) Integer published,
                                   @RequestParam(value = "content", required = false) String content,
                                   @RequestParam(value = "categoryId", required = false) Long categoryId,
                                       @CurrentUser UserPrincipal userPrincipal,
                                   @RequestPart("image") MultipartFile filePart) throws IOException {
        QuizRequest quizRequest = QuizRequest.builder()
                .title(title)
                .summary(summary)
                .type(type)
                .score(score)
                .published(published)
                .content(content)
                .categoryId(categoryId)
                .linkImage(filePart)
                .build();
        return quizService.createQuiz(quizRequest, userPrincipal, filePart);
    }


    @PutMapping("/edit/{quizId}")
    public QuizResponse updateQuiz(@RequestBody @Valid QuizRequest quizRequest
            ,@PathVariable("quizId") Long quizId
            , @CurrentUser UserPrincipal userPrincipal
            ,@RequestPart("image") MultipartFile filePart) {
        return quizService.updateQuiz(quizId, quizRequest, userPrincipal, filePart);
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
    public ResponseEntity<?> submitQuiz() {
//        return quizService.tinhDiem();
        Long quizId = Long.valueOf(1);
        Long [] arr = {Long.valueOf(1), Long.valueOf(3), Long.valueOf(4), Long.valueOf(6), Long.valueOf(8), Long.valueOf(9)};
        List<Long> answerIds = Arrays.asList(arr);

        return quizService.submitQuiz(quizId, answerIds);
    }

}
