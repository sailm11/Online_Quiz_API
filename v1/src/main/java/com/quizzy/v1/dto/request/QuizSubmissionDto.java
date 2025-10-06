package com.quizzy.v1.dto.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmissionDto {
    private List<AnswerSubmissionDto> answers = new ArrayList<>();
}
