package com.quizzy.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizResultDto {

    private Integer score;
    private Integer total;
}
