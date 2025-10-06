package com.quizzy.v1.dto;

import com.quizzy.v1.dto.response.QuestionResponseBase;

public class TextQuestionResponse extends QuestionResponseBase {

    public TextQuestionResponse(Long questionId, String quizTitle, String questionText) {
        super(questionId, quizTitle, questionText);
    }

}
