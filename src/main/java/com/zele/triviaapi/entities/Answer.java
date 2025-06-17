package com.zele.triviaapi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {
    @Id
    @JsonIgnore
    private String id;

    private String questionId;
    private String selectedOption;
}
