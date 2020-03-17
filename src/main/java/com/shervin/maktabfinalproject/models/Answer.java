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
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private double grade;

    private boolean isCorrected;

    @ManyToOne
    private Collegian collegian;

    @ManyToOne
    private ExamQuestionsScore examQuestionsScore;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", grade=" + grade +
                ", isCorrected=" + isCorrected +
                ", collegian=" + collegian.getAccount().getUsername() +
                ", examQuestionsScore=" + examQuestionsScore.getId() +
                '}';
    }
}
