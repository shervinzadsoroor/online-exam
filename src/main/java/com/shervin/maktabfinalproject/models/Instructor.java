package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Instructor extends Person{
    private String degree;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses = new HashSet<>();
}
