package com.quizzy.v1.service;

import java.util.List;

import com.quizzy.v1.dto.request.QuizDto;
import com.quizzy.v1.dto.request.TitleDto;
import com.quizzy.v1.dto.response.QuizResponseDto;

public interface QuizService {

    String createQuiz(QuizDto quizDto, Long quizId);

    List<QuizResponseDto> getQuiz();

    String createQuizTitle(TitleDto title);

    String deleteQuiz(Long quizId);
}
