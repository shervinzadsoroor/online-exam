package com.shervin.maktabfinalproject.crudrepositories.courserepository;

import com.shervin.maktabfinalproject.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/course")
@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/create")
    public String sendCreationForm(Model model) {
        model.addAttribute("course", new Course());
        return "defineNewCourse";
    }
}
