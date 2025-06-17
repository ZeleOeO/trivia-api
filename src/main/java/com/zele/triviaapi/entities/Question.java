package com.zele.triviaapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    @Id
    @JsonProperty("question_id")
    private String id;

    private String questionText;

    @ElementCollection
    @MapKeyColumn(name = "option_key")
    @Column(name = "option_value")
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    private Map<String, String> options = new HashMap<>();
    private String category;
    private String type;
}
