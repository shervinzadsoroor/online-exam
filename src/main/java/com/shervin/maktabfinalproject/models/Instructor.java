package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Instructor extends Person {
    private String degree;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "instructor")
    private List<Exam> exams = new ArrayList<>();

    @OneToMany(mappedBy = "instructor")
    private List<Question> questions = new ArrayList<>();
}
