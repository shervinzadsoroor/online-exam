package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Exam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double score;
    private double duration;
    private String heldDate;
    //    private Time startingTime;
    private boolean isMultipleChoice;
    private boolean isActive;

    @ManyToOne
    private Course course;

    @ManyToMany(mappedBy = "participatedExams")
    private List<Collegian> participatedCollegians = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();

    @ManyToOne
    private Instructor instructor;

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", duration=" + duration +
                ", heldDate='" + heldDate + '\'' +
                ", isMultipleChoice=" + isMultipleChoice +
                ", course=" + course.getId() +
                ", instructor=" + instructor.getId() +
                '}';
    }
}