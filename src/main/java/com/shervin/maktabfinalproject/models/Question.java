package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String title;
    private boolean isMultipleChoice;

    @OneToMany(mappedBy = "question")
    private List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();

    @ManyToOne
    private Instructor instructor;

    @ManyToOne
    private Course course;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
//    private List<QuestionOption> options = new ArrayList<>();

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", isMultipleChoice=" + isMultipleChoice +
                ", instructor=" + instructor.getLastName() +
                ", examQuestionScores=" + examQuestionsScores.toString() +
                '}';
    }
}
