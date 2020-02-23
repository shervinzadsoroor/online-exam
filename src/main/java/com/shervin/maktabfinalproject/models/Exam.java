package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;
    private Time duration;
    private Date heldDate;
    private Time startingTime;
    private boolean isMultipleChoice;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "exam")
    private Set<Question> questions = new HashSet<>();

}