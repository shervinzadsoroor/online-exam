package com.shervin.maktabfinalproject.crudrepositories.courserepository;

import com.shervin.maktabfinalproject.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> showAllCourses(){
        return courseRepository.findAll();
    }

    public Course findById(Long id){
        return courseRepository.findById(id).get();
    }
}
