package com.zele.triviaapi.repositories;

import com.zele.triviaapi.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, String> {
}
