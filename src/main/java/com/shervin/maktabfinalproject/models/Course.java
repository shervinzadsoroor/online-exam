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
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
//    private Date startDate;
    private String startDate;
//    private Date endDate;
    private String endDate;
    private boolean isActive;
    private boolean hasInstructor;

    @OneToMany(mappedBy = "course")
    private List<Exam> exams = new ArrayList<>();

    @ManyToOne
    private Instructor instructor;

    @OneToMany(mappedBy = "course")
    private List<Question> questions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "course_collegian",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "collegian_id", nullable = false)})
    private List<Collegian> collegians = new ArrayList<>();
}
