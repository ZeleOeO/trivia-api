package com.zele.triviaapi.repositories;

import com.zele.triviaapi.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, String> {
}
