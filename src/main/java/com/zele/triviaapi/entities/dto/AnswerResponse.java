package com.zele.triviaapi.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {
    private String quiz_id;
    private int score;
    private int total_questions;
    private List<Answer> answers = new ArrayList<>();
}
