package com.zele.triviaapi.controllers;

import com.zele.triviaapi.entities.Quiz;
import com.zele.triviaapi.entities.api.DefaultAPIResponse;
import com.zele.triviaapi.entities.Question;
import com.zele.triviaapi.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/start-quiz")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;


    @GetMapping("")
    public ResponseEntity<DefaultAPIResponse<List<Question>>> getQuestions(
            @RequestParam Integer num, @RequestParam Integer cat
    ) {
        return questionService.getQuestions(num, cat);
    }

    @GetMapping("{id}")
    public ResponseEntity<DefaultAPIResponse<Quiz>> getQuiz(@PathVariable String id) {
        return questionService.getQuiz(id);
    }
}
