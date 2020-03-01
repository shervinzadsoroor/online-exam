package com.shervin.maktabfinalproject.crudrepositories.examrepository;

import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.models.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/exam")
@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/list/{id}")
    public String sendListOfExamsOfCourse(@PathVariable("id") Long courseId, Model model) {
        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        return "allExams";
    }

    @GetMapping("/edit/{id}")
    public String sendExamEditForm(@PathVariable("id") Long examId, Model model) {
        model.addAttribute("exam", examService.findById(examId));
        return "editExam";
    }

    @PostMapping("/edit")
    public String editExam(@ModelAttribute Exam exam, Model model) {
        examService.saveExam(exam);
        //after saving the edited exam, the following lines collects the required info of the course
        //and redirects to allExams page
        model.addAttribute("exams", examService.findAllExamsByCourseId(exam.getCourse().getId()));
        model.addAttribute("course", courseService.findById(exam.getCourse().getId()));
        return "allExams";
    }

    @GetMapping("/delete/{id}")
    public String showConfirmationForDelete(@PathVariable("id") Long examId, Model model) {
        model.addAttribute("exam", examService.findById(examId));
        return "deleteExamConfirmation";
    }

    @PostMapping("/delete/{id}")
    public String deleteExam(@PathVariable("id") Long examId, Model model) {
        Exam exam = examService.findById(examId);
        Long courseId = exam.getCourse().getId();
        examService.deleteExamById(examId);
        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        return "allExams";
    }
}
