package com.quizzy.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quizzy.v1.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
