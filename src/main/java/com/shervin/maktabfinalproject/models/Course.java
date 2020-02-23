package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    @OneToMany(mappedBy = "course")
    private Set<Exam> exams = new HashSet<>();

    @ManyToOne
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "course_collegian",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "collegian_id", nullable = false)})
    private Set<Collegian> collegians = new HashSet<>();
}
