package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private boolean isCorrect = false;
    // it is not allowed to use name 'order' because of SQL syntax error and it is a reserved key word
    private int orderOfOption;

    @ManyToOne
    private MultipleChoiceQuestion multipleChoiceQuestion;

//    @ManyToOne
//    private Question question;


    @Override
    public String toString() {
        return "QuestionOption{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isCorrect=" + isCorrect +
                ", orderOfOption=" + orderOfOption +
//                ", multipleChoiceQuestion=" + multipleChoiceQuestion +
                '}';
    }
}
