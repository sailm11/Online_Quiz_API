package com.quizzy.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Answers")
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String answerText;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean correct = false;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(String string, boolean b) {

        this.answerText = string;
        this.correct = b;
    }

    public Answer(long l, String string, boolean b) {
        this.answerText = string;
        this.correct = b;
        this.id = l;

    }
}
