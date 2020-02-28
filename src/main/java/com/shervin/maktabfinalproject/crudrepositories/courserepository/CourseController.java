package com.shervin.maktabfinalproject.crudrepositories.courserepository;

import com.shervin.maktabfinalproject.crudrepositories.collegianrepository.CollegianService;
import com.shervin.maktabfinalproject.crudrepositories.instructorrepository.InstructorService;
import com.shervin.maktabfinalproject.models.Course;
import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/course")
@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private CollegianService collegianService;

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

    @GetMapping("/addInstructor/{id}")
    public String showInstructorsToChooseOne(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("instructors", instructorService.findAll());
        return "addInstructorToCourse";
    }

    @PostMapping("/addInstructor")
    public String addInstructor(@ModelAttribute Course course, Model model) {
        course.setHasInstructor(true);
        courseService.saveCourse(course);
        model.addAttribute("courses", courseService.showAllCourses());
        return "allCourses";
    }

    @GetMapping("/addCollegians/{id}")
    public String sendTableOfCollegiansForAdding(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("collegians", collegianService.finaAll());
        return "addCollegiansToCourse";
    }

    @PostMapping("/addCollegians")
    public String addCollegians(@ModelAttribute Course course, Model model) {
        courseService.saveCourse(course);
        model.addAttribute("courses", courseService.showAllCourses());
        return "allCourses";
    }

    @GetMapping("/details/{id}")
    public String showCourseDetails(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return "courseDetails";
    }
}
