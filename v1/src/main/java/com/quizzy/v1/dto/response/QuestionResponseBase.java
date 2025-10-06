package com.quizzy.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class QuestionResponseBase {

    private Long questionId;
    private String quizTitle;
    private String questionText;
}
