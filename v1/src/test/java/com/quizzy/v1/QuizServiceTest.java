package com.quizzy.v1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.quizzy.v1.dto.request.AnswerSubmissionDto;
import com.quizzy.v1.dto.request.QuizSubmissionDto;
import com.quizzy.v1.dto.response.QuizResultDto;
import com.quizzy.v1.model.Answer;
import com.quizzy.v1.model.Question;
import com.quizzy.v1.model.Quiz;
import com.quizzy.v1.repository.AnswerRepository;
import com.quizzy.v1.repository.QuizRepository;
import com.quizzy.v1.service.QuizEvaluationServiceImpl;
import com.quizzy.v1.service.QuizServiceImpl;

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    @InjectMocks
    private QuizEvaluationServiceImpl evaluationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEvaluateQuiz_TextQuestionWithKeywordMatch() {
        System.out.println("Testing.....");
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("AI Quiz");

        Question q1 = new Question();
        q1.setId(101L);
        q1.setQuestionText("Define AI");
        q1.setType("TEXT");
        q1.setCorrectAnswer("Artificial Intelligence is the simulation of human intelligence by machines.");

        quiz.setQuestion(List.of(q1));

        QuizSubmissionDto submission = new QuizSubmissionDto();
        submission.setAnswers(List.of(
                new AnswerSubmissionDto(101L, null, "AI is the simulation of human intelligence by machines")));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        QuizResultDto result = evaluationService.evaluateQuiz(1L, submission);

        assertNotNull(result);
        System.out.println(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getScore());
    }

    @Test
    void testEvaluateQuiz_SingleChoiceCorrectAnswer() {
        Quiz quiz = new Quiz();
        quiz.setId(2L);
        quiz.setTitle("Math Quiz");
        System.out.println("SinglChoice");
        Question q1 = new Question();
        q1.setId(201L);
        q1.setQuestionText("2 + 2 = ?");
        q1.setType("SINGLE");

        Answer correct = new Answer(1L, "4", true);
        Answer wrong = new Answer(2L, "5", false);
        q1.setAnswers(List.of(correct, wrong));

        quiz.setQuestion(List.of(q1));

        QuizSubmissionDto submission = new QuizSubmissionDto();
        submission.setAnswers(List.of(
                new AnswerSubmissionDto(201L, 1L)));
        System.out.println(submission);
        when(quizRepository.findById(2L)).thenReturn(Optional.of(quiz));
        when(answerRepository.findByQuestionIdAndCorrectTrue(201L))
                .thenReturn(List.of(correct));

        QuizResultDto result = evaluationService.evaluateQuiz(2L, submission);
        assertNotNull(result);
        System.out.println(result);
        assertEquals(1, result.getScore());
        assertEquals(1, result.getTotal());
    }

    @Test
    void testEvaluateQuiz_MultipleChoiceQuestions() {
        System.out.println("MChoice");

        Quiz quiz = new Quiz();
        quiz.setId(3L);
        quiz.setTitle("Java Features");

        Question q1 = new Question();
        q1.setId(301L);
        q1.setQuestionText("Select Java Features");
        q1.setType("MULTIPLE");

        Answer a1 = new Answer(1L, "OOP", true);
        Answer a2 = new Answer(2L, "Platform Independent", true);
        Answer a3 = new Answer(3L, "Slow", false);
        q1.setAnswers(List.of(a1, a2, a3));

        quiz.setQuestion(List.of(q1));

        QuizSubmissionDto submission = new QuizSubmissionDto();
        submission.setAnswers(List.of(
                new AnswerSubmissionDto(301L, null, null, List.of(1L, 2L))));
        System.out.println(submission);
        when(quizRepository.findById(3L)).thenReturn(Optional.of(quiz));
        when(answerRepository.findByQuestionIdAndCorrectTrue(301L))
                .thenReturn(List.of(a1, a2));

        QuizResultDto result = evaluationService.evaluateQuiz(3L, submission);
        assertNotNull(result);
        System.out.println(result);
        assertEquals(1, result.getScore());
        assertEquals(1, result.getTotal());
    }

    @Test
    void testEvaluateQuiz_QuizNotFound() {
        when(quizRepository.findById(99L)).thenReturn(Optional.empty());

        QuizSubmissionDto submission = new QuizSubmissionDto();
        submission.setAnswers(Collections.emptyList());

        Exception ex = assertThrows(RuntimeException.class, () -> evaluationService.evaluateQuiz(99L, submission));
        System.out.println(submission.toString());
        assertEquals("Quiz not found", ex.getMessage());
    }
}
