package com.zele.triviaapi.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String question_id;
    private boolean correct;
    private String correct_answer;
    private String selected_option;
}
