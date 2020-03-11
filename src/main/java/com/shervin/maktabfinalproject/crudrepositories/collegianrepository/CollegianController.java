package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.crudrepositories.examrepository.ExamService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping("/collegian")
@Controller
public class CollegianController {
    private static long examTimeMins = 1;
    @Autowired
    private CollegianService collegianService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ExamService examService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String CollegianDashboard() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "redirect:/collegian/dashboard/"+userName;
    }

    @GetMapping("/dashboard/{userName}")
    public String showCollegianDashboard(@PathVariable("userName") String username, Model model) {

        //if collegian account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the collegian
        Account account = accountService.findAccountByUsername(username);

        if (account.getStatus().equalsIgnoreCase("not registered")) {

            Collegian collegian = new Collegian();
            collegian.setAccount(account);

            model.addAttribute("collegian", collegian);

            return "registrationFormForCollegian";

        } else if (account.getStatus().equalsIgnoreCase("waiting")) {
            model.addAttribute("account", account);
            return "registrationWaitingDetails";

        }
//        else if (account.getStatus().equalsIgnoreCase("registered")){
//
//        }
        model.addAttribute("collegian", (Collegian) account.getPerson());
        return "collegianDashboard";
    }

    @PostMapping("/register")
    public String registerCollegian(@ModelAttribute Collegian collegian) {
        collegian.getAccount().setStatus("waiting");
        collegianService.saveCollegian(collegian);
        return "registrationSuccessful";
    }

    //showing the list of exams to collegian
    @GetMapping("/course-exam-list/{courseId}/{colId}")
    public String sendListOfExamsOfCourseForCollegian(@PathVariable("courseId") Long courseId,
                                                      @PathVariable("colId") Long collegianId,
                                                      Model model) {
        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        model.addAttribute("collegian", collegianService.findById(collegianId));

        return "allExamsForCollegian";
    }

    @GetMapping("/participate-exam/{examId}/{collegianId}")
    public String participateExam(@PathVariable("examId") Long examId,
                                  @PathVariable("collegianId") Long collegianId,
                                  Model model, HttpServletRequest request) {
        Exam exam = examService.findById(examId);
        Date date = Calendar.getInstance().getTime();
//        Long finish = System.currentTimeMillis() + ((long)exam.getDuration()*60_000);
        request.getSession().setAttribute("examStarted", date.getTime());
//        request.getSession().setAttribute("examStarted", protocol.getStart().getTime());
        final int remaining = getRemainingTime(request);
        model.addAttribute("examTime", remaining);

        return "startExam";
    }

    @RequestMapping(value = "/time")
    @ResponseBody
    public Integer timer(HttpServletRequest request) {
        return getRemainingTime(request);
    }

    private int getRemainingTime(HttpServletRequest request) {
        final long start = (long) request.getSession().getAttribute("examStarted");
        final int remaining = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
        return remaining;
    }

}
