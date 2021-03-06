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

    //useful for checking each exam if is been participated by collegian
    private transient boolean isBeenParticipated;
    private transient boolean isRunning;

    @ManyToOne
    private Course course;

    @ManyToMany
    @JoinTable(name = "exams_participated_collegians",
            joinColumns = {@JoinColumn(name = "exam_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "collegian_id", nullable = false)})
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