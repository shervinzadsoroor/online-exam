package com.shervin.maktabfinalproject.crudrepositories.courserepository;

import com.shervin.maktabfinalproject.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/create")
    public String sendCreationForm(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "managerDashboard";
    }

    @GetMapping("/all")
    public String sendAllCourses(Model model) {
        model.addAttribute("courses", courseService.showAllCourses());
        return "allCourses";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id") Long courseId, Model model) {
        model.addAttribute("courseForEdit", courseService.findById(courseId));
        return "editCourse";
    }

    @PostMapping("/edit/save")
    public String saveChangesToCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "managerDashboard";
    }
}
