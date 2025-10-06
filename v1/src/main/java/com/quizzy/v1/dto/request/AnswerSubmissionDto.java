package com.quizzy.v1.dto.request;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSubmissionDto {
    private Long selectedAnswerId;
    private List<Long> selectedAnswerIds = new ArrayList<>();
    private String userAnswerText;
    private Long questionId;

    public AnswerSubmissionDto(long l, Object object, String string) {
        this.questionId = l;
        this.userAnswerText = string;
    }

    public AnswerSubmissionDto(long l, Long l2) {
        this.questionId = l;
        this.selectedAnswerId = l2;
    }

    public AnswerSubmissionDto(long l, Object object, Object object2, List<Long> of) {
        this.questionId = l;
        this.selectedAnswerIds = of;
    }
}
