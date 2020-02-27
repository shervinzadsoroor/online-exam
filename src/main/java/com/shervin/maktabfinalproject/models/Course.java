package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

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
//    private Date startDate;
    private String startDate;
//    private Date endDate;
    private String endDate;
    private boolean isActive;
    private boolean hasInstructor;

    @OneToMany(mappedBy = "course")
    private Set<Exam> exams = new HashSet<>();

    @ManyToOne
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "course_collegian",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "collegian_id", nullable = false)})
    private List<Collegian> collegians = new ArrayList<>();
}
