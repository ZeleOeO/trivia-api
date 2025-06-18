package com.zele.triviaapi.entities;

import com.zele.triviaapi.utils.IDGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Quiz {
    @Id
    private String id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.id = IDGenerator.generateQuizID();
    }

}