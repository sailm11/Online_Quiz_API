package com.quizzy.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizzy.v1.dto.request.QuizDto;
import com.quizzy.v1.dto.request.QuizSubmissionDto;
import com.quizzy.v1.dto.request.TitleDto;
import com.quizzy.v1.dto.response.QuizResponseDto;
import com.quizzy.v1.dto.response.QuizResultDto;
import com.quizzy.v1.service.QuizEvaluationService;
import com.quizzy.v1.service.QuizService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizEvaluationService quizEvaluationService;

    @PostMapping("/title")
    @Tag(name = "Quiz API")
    public ResponseEntity<String> createQuizTitle(@Valid @RequestBody TitleDto title) {
        String response = quizService.createQuizTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{quizId}/create")
    @Tag(name = "Quiz API")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto, @PathVariable Long quizId) {
        String msg = quizService.createQuiz(quizDto, quizId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Tag(name = "Quiz API")
    @GetMapping("/get")
    public ResponseEntity<List<QuizResponseDto>> getQuiz() {
        List<QuizResponseDto> quizResponse = quizService.getQuiz();
        return new ResponseEntity<>(quizResponse, HttpStatus.OK);
    }

    @PostMapping("/{quizId}/submit")
    @Tag(name = "Quiz API")
    public QuizResultDto submitQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizSubmissionDto submission) {

        return quizEvaluationService.evaluateQuiz(quizId, submission);
    }

    @DeleteMapping("/{quizId}/delete")
    @Tag(name = "Quiz API")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId) {
        String response = quizService.deleteQuiz(quizId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
