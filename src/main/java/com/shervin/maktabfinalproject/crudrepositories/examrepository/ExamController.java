package com.shervin.maktabfinalproject.crudrepositories.examrepository;

import com.shervin.maktabfinalproject.crudrepositories.answerrepository.AnswerService;
import com.shervin.maktabfinalproject.crudrepositories.collegianrepository.CollegianService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository.ExamQuestionsScoreService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/exam")
@Controller
public class ExamController {

    private final ExamService examService;
    private final CourseService courseService;
    private final ExamQuestionsScoreService examQuestionsScoreService;
    private final AnswerService answerService;
    private final CollegianService collegianService;

    @Autowired
    public ExamController(ExamService examService, CourseService courseService, ExamQuestionsScoreService examQuestionsScoreService, AnswerService answerService, CollegianService collegianService) {
        this.examService = examService;
        this.courseService = courseService;
        this.examQuestionsScoreService = examQuestionsScoreService;
        this.answerService = answerService;
        this.collegianService = collegianService;
    }

    //showing the list of exams to instructor
    @GetMapping("/list/{id}")
    public String sendListOfExamsOfCourse(@PathVariable("id") Long courseId, Model model) {
        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        return "allExams";
    }

    //    //showing the list of exams to collegian
//    @GetMapping("/collegian-exam-list/{id}")
//    public String sendListOfExamsOfCourseForCollegian(@PathVariable("id") Long courseId, Model model) {
//        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
//        model.addAttribute("course", courseService.findById(courseId));
//        return "allExamsForCollegian";
//    }
    @GetMapping("/create/{id}")
    public String sendCreateForm(@PathVariable("id") Long courseId, Model model) {
        Exam exam = new Exam();
        Course course = courseService.findById(courseId);
        exam.setCourse(course);
        exam.setInstructor(course.getInstructor());
        model.addAttribute("exam", exam);
        return "createExam";
    }

    @PostMapping("/create")
    public String createExamByInstructor(@ModelAttribute Exam exam, Model model) {
        examService.saveExam(exam);
        //after saving the new exam, we update and show the list of exams of this course by the following codes
        model.addAttribute("exams", examService.findAllExamsByCourseId(exam.getCourse().getId()));
        model.addAttribute("course", courseService.findById(exam.getCourse().getId()));
        return "allExams";
    }

    @GetMapping("/edit/{id}")
    public String sendExamEditForm(@PathVariable("id") Long examId,
                                   Model model,
                                   HttpServletRequest request) {
        Exam exam = examService.findById(examId);
        // TODO: 3/15/20  i know this is redundant and needs refactor
        model.addAttribute("exam", exam);
        request.getSession().setAttribute("exam", exam);
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

    @GetMapping("/questions-list/{id}")
    public String showExamQuestions(@PathVariable("id") Long examId, Model model) {
        Exam exam = examService.findById(examId);
        List<ExamQuestionsScore> list = exam.getExamQuestionsScores();
        model.addAttribute("examQuestionScores", list);
        return "allQuestionsOfExam";
    }

    @GetMapping("/questions/assign-score/{id}")
    public String showQuestionsOfExamToAssignScore(@PathVariable("id") Long examId, Model model) {

        Exam exam = examService.findById(examId);
        model.addAttribute("exam", exam);

        return "allQuestionsToAssignScore";
    }

    @PostMapping("/questions/assign-score")
    public String assignScoreToQuestionsOfExam(@ModelAttribute Exam exam,
                                               HttpServletRequest request) {
        double sum = 0;
        for (ExamQuestionsScore eqs : exam.getExamQuestionsScores()) {
            examQuestionsScoreService.save(eqs);
            sum += eqs.getScore();
        }
        exam.setScore(sum);
        Exam persistedExam = examService.saveExam(exam);

        //we set the exam attribute again because the exam have been changed
        request.getSession().setAttribute("exam", persistedExam);
        return "redirect:/exam/edit/" + persistedExam.getId();
    }

    @GetMapping("/participant-list/{id}")
    public String showListOfParticipants(@PathVariable("id") Long examId,
                                         Model model,
                                         HttpServletRequest request) {

        Exam exam = examService.findById(examId);
        List<Collegian> collegians = exam.getParticipatedCollegians();
        request.getSession().setAttribute("examId", examId);
        model.addAttribute("participatedCollegians", collegians);

        return "allParticipants";
    }

    @GetMapping("/participant-answers/{collegianId}/{examId}")
    public String showAnswersOfParticipants(@PathVariable("collegianId") Long collegianId,
                                            @PathVariable("examId") Long examId,
                                            Model model) {
//        Collegian collegian = collegianService.findById(collegianId);
        List<Answer> answers = answerService.findAllAnswersByCollegianIdAndExamId(collegianId, examId);
        Collegian collegian = new Collegian(null, null, null, answers);
        model.addAttribute("collegian", collegian);
        return "collegianExam";
    }

    @PostMapping("/participant-scores-register")
    public String registerScoresOfParticipants(@ModelAttribute Collegian collegian,
                                               Model model) {
        Long examId = collegian.getAnswers().get(0).getExamQuestionsScore().getExam().getId();
        for (Answer answer : collegian.getAnswers()) {
            if (!answer.getExamQuestionsScore().getQuestion().isMultipleChoice()) {
                answer.setCorrected(true);
                answerService.saveAnswer(answer);
            }
        }
        return "redirect:/exam/participant-list/" + examId;
    }
}
