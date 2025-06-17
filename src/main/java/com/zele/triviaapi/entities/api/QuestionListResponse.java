package com.zele.triviaapi.entities.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionListResponse {
    private int responseCode;
    private List<QuestionResponse> results;
}
