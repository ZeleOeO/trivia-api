package com.zele.triviaapi.service.impl;

import com.zele.triviaapi.entities.Question;
import com.zele.triviaapi.entities.Quiz;
import com.zele.triviaapi.entities.api.DefaultAPIResponse;
import com.zele.triviaapi.entities.api.QuestionListResponse;
import com.zele.triviaapi.mapper.QuestionMapper;
import com.zele.triviaapi.repositories.QuizRepository;
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
    private final QuizRepository quizRepository;
    @Value("${api.base-url:https://opentdb.com/api.php}")
    private String BASE_URL;
    private final RestTemplate restTemplate;

    public QuestionServiceImpl(RestTemplate restTemplate, QuestionMapper questionMapper, QuizRepository quizRepository) {
        this.restTemplate = restTemplate;
        this.questionMapper = questionMapper;
        this.quizRepository = quizRepository;
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
            if (questionListResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultAPIResponse<>(HttpStatus.NOT_FOUND.value(), "Response not Found", null));
            }
            questions = questionListResponse.getResults().stream().map(questionMapper::questionResponseToQuestion).toList();
            Quiz quiz = new Quiz(questions);
            quizRepository.save(quiz);
        } catch (RestClientException ex) {
            log.error("Error calling external API at URL: {}", BASE_URL, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultAPIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new DefaultAPIResponse<>(HttpStatus.OK.value(), "Success", questions)
        );
    }

    @Override
    public ResponseEntity<DefaultAPIResponse<Quiz>> getQuiz(String questionId) {
        log.info("Getting quiz with id {}", questionId);
        try {
            var quiz = quizRepository.findById(questionId).orElse(null);
            if (quiz == null) {
                log.error("Quiz with id {} not found", questionId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultAPIResponse<>(HttpStatus.NOT_FOUND.value(), "Quiz not Found", null));
            }
            log.info("Quiz with id {} found", questionId);
            return ResponseEntity.status(HttpStatus.OK).body(new DefaultAPIResponse<>(HttpStatus.OK.value(), "Success", quiz));

        } catch (RestClientException ex) {
            log.error("Error calling external API at URL: {}", BASE_URL, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultAPIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null));
        }
    }
}
