package com.shervin.maktabfinalproject.crudrepositories.courserepository;

import com.shervin.maktabfinalproject.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findAllByOrderByIdDesc();

    List<Course> findAllByInstructor_IdOrderByIdDesc(Long id);
}
