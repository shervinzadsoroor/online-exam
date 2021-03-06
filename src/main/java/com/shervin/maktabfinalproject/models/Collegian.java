package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Collegian extends Person {
    private Double GPA;

    @ManyToMany(mappedBy = "collegians")
    private List<Course> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "participatedCollegians")
    private List<Exam> participatedExams = new ArrayList<>();


    //each answer has some data in its own class such as question and exam in examQuestionScore object
    @OneToMany(mappedBy = "collegian")
    private List<Answer> answers = new ArrayList<>();

}
