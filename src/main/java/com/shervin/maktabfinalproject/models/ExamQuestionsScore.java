package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

/**
 * the reason of creating this entity is that
 * every question needs to have different score in various exams
 * so we need a join table with an extra column called score
 */

public class ExamQuestionsScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @EmbeddedId
//    private ExamQuestionsScoreId examQuestionsScoreId;

    //    @Id
    @ManyToOne
//    @JoinColumn
    private Exam exam;

    //    @Id
    @ManyToOne
//    @JoinColumn
    private Question question;

    private double score;

    @Override
    public String toString() {
        return "ExamQuestionsScore{" +
//                "id=" + id +
                ", exam=" + exam.getTitle() +
//                ", question=" + question.getTitle() +
//                ", score=" + score +
                '}';
    }
}