package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MultipleChoiceQuestion extends Question {

    private String answerKey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "multipleChoiceQuestion")
    private List<QuestionOption> options = new ArrayList<>();

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                "answerKey='" + answerKey + '\'' +
                ", options=" + options +
                '}';
    }
}
