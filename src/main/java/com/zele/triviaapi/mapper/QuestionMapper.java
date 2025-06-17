package com.zele.triviaapi.mapper;

import com.zele.triviaapi.entities.Question;
import com.zele.triviaapi.entities.api.QuestionResponse;
import com.zele.triviaapi.utils.IDGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionMapper {
    public Question questionResponseToQuestion(QuestionResponse questionResponse) {
        Question question = new Question();
        List<String> answers = new ArrayList<>(questionResponse.getIncorrect_answers());
        answers.add(questionResponse.getCorrect_answer());
        Map<String, String> options = new HashMap<>();
        answers.forEach(answer -> options.put(String.valueOf( (char) (answers.indexOf(answer) + 'A')), answer));
        question.setId(IDGenerator.generateQuestionID());
        question.setQuestionText(questionResponse.getQuestion());
        question.setType(questionResponse.getType());
        question.setCategory(questionResponse.getCategory());
        question.setOptions(options);
        return question;
    }
}
