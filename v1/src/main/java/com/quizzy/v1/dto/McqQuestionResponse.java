package com.quizzy.v1.dto;

import java.util.List;

import com.quizzy.v1.dto.response.OptionResponseDto;
import com.quizzy.v1.dto.response.QuestionResponseBase;

import lombok.Getter;

@Getter
public class McqQuestionResponse extends QuestionResponseBase {
    private List<OptionResponseDto> options;

    public McqQuestionResponse(Long questionId, String quizTitle, String questionText,
            List<OptionResponseDto> options) {
        super(questionId, quizTitle, questionText);
        this.options = options;
    }

}
