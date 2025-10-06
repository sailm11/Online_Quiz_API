package com.quizzy.v1.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quizzy.v1.dto.request.AnswerSubmissionDto;
import com.quizzy.v1.dto.request.QuizSubmissionDto;
import com.quizzy.v1.dto.response.QuizResultDto;
import com.quizzy.v1.model.Answer;
import com.quizzy.v1.model.Question;
import com.quizzy.v1.model.Quiz;
import com.quizzy.v1.repository.AnswerRepository;
import com.quizzy.v1.repository.QuizRepository;

@Service
public class QuizEvaluationServiceImpl implements QuizEvaluationService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public QuizResultDto evaluateQuiz(Long quizId, QuizSubmissionDto quizSubmissionDto) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        int score = 0;
        int total = quizSubmissionDto.getAnswers().size();

        for (AnswerSubmissionDto userAnswer : quizSubmissionDto.getAnswers()) {
            Question question = quiz.getQuestion().stream()
                    .filter(q -> q.getId().equals(userAnswer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            switch (question.getType().toUpperCase()) {
                case "TEXT" -> {
                    double match = keywordMatch(question.getCorrectAnswer(), userAnswer.getUserAnswerText());
                    if (match >= 0.8)
                        score++;
                }

                case "SINGLE" -> {
                    boolean isCorrect = question.getAnswers().stream()
                            .filter(Answer::isCorrect)
                            .anyMatch(a -> a.getId().equals(userAnswer.getSelectedAnswerId()));
                    if (isCorrect)
                        score++;
                }

                case "MULTIPLE" -> {
                    Set<Long> correctIds = question.getAnswers().stream()
                            .filter(Answer::isCorrect)
                            .map(Answer::getId)
                            .collect(Collectors.toSet());

                    Set<Long> selectedIds = new HashSet<>(userAnswer.getSelectedAnswerIds());
                    if (selectedIds.equals(correctIds))
                        score++;
                }
            }
        }
        return new QuizResultDto(score, total);
    }

    private double keywordMatch(String correctAnswer, String userAnswer) {
        String[] correctWords = correctAnswer.toLowerCase().split("\\s+");
        String userLower = userAnswer.toLowerCase();
        long matched = Arrays.stream(correctWords)
                .filter(userLower::contains)
                .count();
        double ratio = (double) matched / correctWords.length;
        return ratio;
    }

}
