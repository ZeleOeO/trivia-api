package com.zele.triviaapi.service.impl;

import com.zele.triviaapi.entities.Question;
import com.zele.triviaapi.entities.Quiz;
import com.zele.triviaapi.entities.api.DefaultAPIResponse;
import com.zele.triviaapi.entities.api.QuestionListResponse;
import com.zele.triviaapi.entities.dto.Answer;
import com.zele.triviaapi.entities.dto.AnswerRequest;
import com.zele.triviaapi.entities.dto.AnswerResponse;
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
import java.util.Objects;

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

    @Override
    public ResponseEntity<DefaultAPIResponse<AnswerResponse>> submitQuiz(AnswerRequest answerRequest) {
        try {
            var quiz = quizRepository.findById(answerRequest.getQuiz_id()).orElse(null);
            if (quiz == null) {
                log.error("Quiz with id {} not found", answerRequest.getQuiz_id());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultAPIResponse<>(HttpStatus.NOT_FOUND.value(), "Quiz not found", null));
            }
            log.info("Quiz with id {} found", answerRequest.getQuiz_id());
            log.info("Submitting quiz with id {}", answerRequest.getQuiz_id());
            return ResponseEntity.status(HttpStatus.OK).body(solveQuiz(answerRequest,  quiz));
        } catch (RestClientException ex) {
            log.error("Error calling external API at URL: {}", BASE_URL, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultAPIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null));
        }
    }

    private DefaultAPIResponse<AnswerResponse> solveQuiz(AnswerRequest answerRequest, Quiz quiz) {
        if (quiz.getQuestions().size() !=  answerRequest.getAnswers().size()) {
            log.error("Questions and Answers do not match");
            return new DefaultAPIResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), null);
        }
        int score = 0;
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setQuiz_id(quiz.getId());
        answerResponse.setTotal_questions(quiz.getQuestions().size());
        for (int i = 0; i < answerRequest.getAnswers().size(); i++) {
            Answer answer = answerRequest.getAnswers().get(i);
            answer.setCorrect_answer(quiz.getQuestions().get(i).getCorrectAnswer());
            if (Objects.equals(answerRequest.getAnswers().get(i).getSelected_option(), quiz.getQuestions().get(i).getCorrectAnswer())) {
                score++;
                answer.setCorrect(true);
                answerResponse.getAnswers().add(answer);
            } else {
                if (score>0) i--;
                answer.setCorrect(false);
                answerResponse.getAnswers().add(answer);
            }
        }
        answerResponse.setScore(score);
        return new DefaultAPIResponse<>(HttpStatus.OK.value(), "Success", answerResponse);
    }
}
