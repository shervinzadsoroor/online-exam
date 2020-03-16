package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
