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

public class Collegian  extends Person{
    private Double GPA;

    @ManyToMany(mappedBy = "collegians")
    private Set<Course>courses = new HashSet<>();
}
