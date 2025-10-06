package com.quizzy.v1.service;

import com.quizzy.v1.dto.request.QuizSubmissionDto;
import com.quizzy.v1.dto.response.QuizResultDto;

public interface QuizEvaluationService {
    QuizResultDto evaluateQuiz(Long quizId, QuizSubmissionDto quizSubmissionDto);

}
