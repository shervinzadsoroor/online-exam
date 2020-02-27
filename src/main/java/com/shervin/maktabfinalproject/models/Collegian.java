package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Collegian  extends Person{
    private Double GPA;

    @ManyToMany(mappedBy = "collegians")
    private List<Course> courses = new ArrayList<>();
}