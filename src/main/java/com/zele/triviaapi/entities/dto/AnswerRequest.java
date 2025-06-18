package com.zele.triviaapi.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private String quiz_id;
    private List<Answer> answers;
}
