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

    @OneToMany(mappedBy = "multipleChoiceQuestion")
    private List<QuestionOption> options = new ArrayList<>();

}
