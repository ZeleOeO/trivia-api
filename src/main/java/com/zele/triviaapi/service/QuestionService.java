package com.zele.triviaapi.service;

import com.zele.triviaapi.entities.Quiz;
import com.zele.triviaapi.entities.api.DefaultAPIResponse;
import com.zele.triviaapi.entities.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    ResponseEntity<DefaultAPIResponse<List<Question>>> getQuestions(int amount, int category);
    ResponseEntity<DefaultAPIResponse<Quiz>> getQuiz(String questionId);
}
