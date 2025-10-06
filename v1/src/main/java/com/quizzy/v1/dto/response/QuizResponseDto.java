package com.quizzy.v1.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponseDto {
    private Long id;
    private String title;
    private List<QuestionResponseBase> questions;
}
