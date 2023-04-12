package com.ptithcm.onlinetest.service;

import com.ptithcm.onlinetest.exception.BadRequestException;
import com.ptithcm.onlinetest.exception.ResourceNotFoundException;
import com.ptithcm.onlinetest.model.*;
import com.ptithcm.onlinetest.payload.dto.QuestionTakeDto;
import com.ptithcm.onlinetest.payload.request.QuizRequest;
import com.ptithcm.onlinetest.payload.response.ApiResponse;
import com.ptithcm.onlinetest.payload.response.PagedResponse;
import com.ptithcm.onlinetest.payload.response.QuizResponse;
import com.ptithcm.onlinetest.repository.*;
import com.ptithcm.onlinetest.security.UserPrincipal;
import com.ptithcm.onlinetest.service.firebase.FirebaseService;
import com.ptithcm.onlinetest.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuizService implements IQuizService{

    @Autowired
    QuizQuestionService quizQuestionService;
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TakeRepository takeRepository;

    @Autowired
    QuizAnswerRepository quizAnswerRepository;

    @Autowired
    FirebaseService service;
    @Override
    public QuizResponse createQuiz(QuizRequest quizRequest, UserPrincipal userPrincipal, MultipartFile fileImage) throws IOException {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        Category category = categoryRepository.findById(quizRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", quizRequest.getCategoryId()));
        if(user == null || category == null)
            return QuizResponse.builder().message("User is not exists").status(false).quiz(null).build();
        if(quizRepository.existsByTitle(quizRequest.getTitle())) {
            return QuizResponse.builder().message("Title already exists").status(false).quiz(null).build();
        }
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
                .linkImage(service.save(fileImage))
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .user(user)
                .category(category)
                .build();
        quizRepository.save(quiz);
        return QuizResponse.builder().message("Create Quiz successfuly").status(true).quiz(quiz).build();
    }

    @Override
    public Iterable<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    @Override
    public QuizResponse updateQuiz(Long quizId, QuizRequest quizRequest, UserPrincipal userPrincipal, MultipartFile fileImage) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if(!quizOptional.isPresent()) {
            return QuizResponse.builder().status(true).message("Update quiz successfully").quiz(quizOptional.get()).build();
        }
        Quiz quiz = quizOptional.get();
        try {
            quiz = Quiz.builder()
                    .title(quizRequest.getTitle())
                    .metaTitle(quizRequest.getMetaTitle())
                    .linkImage(service.save(fileImage))
                    .summary(quizRequest.getSummary())
                    .type(quizRequest.getType())
                    .score(quizRequest.getScore())
                    .published(quizRequest.getPublished())
                    .publishedAt(quizRequest.getPublishedAt())
                    .startsAt(quizRequest.getStartsAt())
                    .endsAt(quizRequest.getEndsAt())
                    .content(quizRequest.getContent())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(userPrincipal != null) {
            Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
            if(userOptional.isPresent()) {
                quiz.setUser(userOptional.get());
            }
        }

        if(quizRequest.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById((quizRequest.getCategoryId()));
            if(categoryOptional.isPresent()) {
                quiz.setCategory(categoryOptional.get());
            }
        }
        quizRepository.save(quiz);
        return QuizResponse.builder().status(true).message("Update quiz successfully").quiz(quiz).build();
    }


    @Override
    public QuizResponse deleteQuiz(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if(quiz.isPresent()) {
            quizRepository.deleteById(id);
            return QuizResponse.builder().status(true).message("Deleted quiz successfully").quiz(quiz.get()).build();
        }
        return QuizResponse.builder().status(false).message("Quiz id is not exist").quiz(null).build();
    }

    @Override
    public ApiResponse createQuizx(QuizRequest quizRequest, UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        Category category = categoryRepository.findById(quizRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", quizRequest.getCategoryId()));
        if(user == null || category == null)
            return ApiResponse.builder().message("User is not exists").status(200).data(null).build();
        if(quizRepository.existsByTitle(quizRequest.getTitle())) {
            return ApiResponse.builder().message("Title already exists").status(200).data(null).build();
        }
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .metaTitle(quizRequest.getMetaTitle())
//                .linkImage(service.save(f))
                .summary(quizRequest.getSummary())
                .type(quizRequest.getType())
                .score(quizRequest.getScore())
                .published(quizRequest.getPublished())
                .publishedAt(quizRequest.getPublishedAt())
                .startsAt(quizRequest.getStartsAt())
                .endsAt(quizRequest.getEndsAt())
                .content(quizRequest.getContent())
                .user(user)
                .category(category)
                .build();
        quizRepository.save(quiz);
        return ApiResponse.builder().message("Create Quiz successfuly").status(200).data(quiz).build();
    }

    @Override
    public PagedResponse<Quiz> getAllQuizzes(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC ,"createdAt");
        Page<Quiz> quizzes = quizRepository.findAll(pageable);
        QuizResponse quizResponse;
        if(quizzes.getTotalElements() == 0 ) {
            return new PagedResponse<>(Collections.emptyList(), quizzes.getNumber(), quizzes.getSize(),
                    quizzes.getTotalElements(), quizzes.getTotalPages(), quizzes.isLast());
        }
        return new PagedResponse<>(quizzes.getContent(), quizzes.getNumber(), quizzes.getSize(),
                quizzes.getTotalElements(), quizzes.getTotalPages(), quizzes.isLast());

    }

    @Override
    public QuestionTakeDto takeQuestion(UserPrincipal userPrincipal, Long quizId) {
        Take take = new Take();
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("QUiz", "id", quizId));
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userPrincipal.getId()));
        take.setStartedAt(Instant.now());
        take.setStatus("started");
        take.setQuiz(quiz);
        take.setUser(user);
        takeRepository.save(take);
        take.setContent(String.valueOf(take.getUser().getId()));
        QuestionTakeDto questionTakeDto = new QuestionTakeDto();
        questionTakeDto.setQuizQuestionDto(quizQuestionService.getQuestionAndAnswerOfQuiz(quizId));
        questionTakeDto.setTake(take);
        return questionTakeDto;
    }
    public ResponseEntity<?> tinhDiem() {
        // list bài quiz, câu hỏi và đáp án đã chọn
//        Set<QuizQuestionDto> questionTakeDtos = question.getQuizQuestionDto();

//        QuizAnswer quizAnswer = quizAnswerRepository.findAllByQuizQuestionId(question.getTake().getQuiz().get)
        Long quizId = Long.valueOf(1);
        List<Map<String, Object>> quizAnswerDtos = quizAnswerRepository.getQuizAnswerTrueByQuizId(quizId);

        return ResponseEntity.ok(quizAnswerDtos);
    }


//    ============================ TEST ==================================
    public ResponseEntity<?> submitQuiz(Long quizId, List<Long> answerIds) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("QUiz", "quizid", quizId));
        List<QuizQuestion> questions = quizQuestionRepository.findByQuizId(quizId);
        List<QuizAnswer> answers = quizAnswerRepository.findByIdIn(answerIds);
        int score = caculateScore(questions, answers);
        return ResponseEntity.ok(score);
    }

    public int caculateScore(List<QuizQuestion> questions, List<QuizAnswer> answers) {
        int correctAnswers = 0;
        for (QuizQuestion question : questions) {
            // đáp án đúng của câu hỏi thứ 1 - 2 - 3 - 4 - ...
            List<QuizAnswer> correctAnswersForQuestion = quizAnswerRepository.findByQuizQuestionIdAndCorrectIsTrue(question.getId());
            System.out.println("correctAnswersForQuestion" + correctAnswersForQuestion);
            List<QuizAnswer> userAnswersForQuestion = answers.stream()
                    .filter(answer -> answer.getQuizQuestion().getId().equals(question.getId()))
                    .collect(Collectors.toList());

            for(QuizAnswer aq:userAnswersForQuestion) {
                if(correctAnswersForQuestion.contains(aq)) {
                    System.out.println("SUCCCCCCCCCEEEEEEEEEEEEESSSSSSSSSS");
                    correctAnswers++;
                }
            }
        }
        int totalQuestions = questions.size();
        return Math.round((float) correctAnswers / totalQuestions * 100);
    }
}
