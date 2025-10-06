package com.quizzy.v1.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private String questionText;
    private String answerType;
    private List<AnswerDto> answers;
    private String userTextAnswer;
}
