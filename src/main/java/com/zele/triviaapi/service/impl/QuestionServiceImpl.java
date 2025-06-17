package com.zele.triviaapi.service.impl;

import com.zele.triviaapi.entities.DefaultAPIResponse;
import com.zele.triviaapi.entities.Question;
import com.zele.triviaapi.entities.api.QuestionListResponse;
import com.zele.triviaapi.mapper.QuestionMapper;
import com.zele.triviaapi.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    @Value("${api.base-url:https://opentdb.com/api.php}")
    private String BASE_URL;
    private final RestTemplate restTemplate;

    public QuestionServiceImpl(RestTemplate restTemplate, QuestionMapper questionMapper) {
        this.restTemplate = restTemplate;
        this.questionMapper = questionMapper;
    }

    @Override
    public ResponseEntity<DefaultAPIResponse<List<Question>>> getQuestions(int amount, int category) {
        List<Question> questions;
        try {
            QuestionListResponse questionListResponse = restTemplate.exchange(
                    BASE_URL + "?amount=" + amount + "&category=" + category,
                    HttpMethod.GET,
                    null,
                    QuestionListResponse.class
            ).getBody();
            if (questionListResponse == null) return ResponseEntity.notFound().build();

            questions = questionListResponse.getResults().stream().map(questionMapper::questionResponseToQuestion).toList();

        } catch (RestClientException ex) {
            log.error("Error calling external API at URL: {}", BASE_URL, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new DefaultAPIResponse<>(HttpStatus.OK.value(), "Success", questions)
        );
    }
}
