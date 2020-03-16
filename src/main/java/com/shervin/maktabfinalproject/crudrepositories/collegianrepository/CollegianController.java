package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.answerrepository.AnswerService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.crudrepositories.examrepository.ExamService;
import com.shervin.maktabfinalproject.crudrepositories.optionrepository.OptionService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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
    @Autowired
    private AnswerService answerService;
    @Autowired
    private OptionService optionService;

    @GetMapping("/dashboard")
    public String CollegianDashboard() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "redirect:/collegian/dashboard/" + userName;
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
                                                      HttpServletRequest request,
                                                      Model model) {

        request.getSession().setAttribute("order", 1);
        request.getSession().setAttribute("isTheFirstTime", true);
        request.getSession().setAttribute("answers", new ArrayList<>());

        model.addAttribute("exams", examService.findAllExamsByCourseId(courseId));
        model.addAttribute("course", courseService.findById(courseId));
        model.addAttribute("collegian", collegianService.findById(collegianId));

        return "allExamsForCollegian";
    }

    @GetMapping("/participate-exam/{examId}/{collegianId}")
    public String participateExam(@PathVariable("examId") Long examId,
                                  @PathVariable("collegianId") Long collegianId,
                                  HttpServletRequest request,
                                  Model model) {

        List<Answer> answers = (List<Answer>) request.getSession().getAttribute("answers");

        Exam exam = examService.findById(examId);
        Collegian collegian = collegianService.findById(collegianId);

        int order = (int) request.getSession().getAttribute("order");

        boolean isTheFirstTime = (boolean) request.getSession().getAttribute("isTheFirstTime");

        //application enters the 'if scope' only for the first time
        if (isTheFirstTime) {

            List<ExamQuestionsScore> eqsList = exam.getExamQuestionsScores();
            int numberOfQuestions = eqsList.size();
            request.getSession().setAttribute("numOfQuestions", numberOfQuestions);
//            for (ExamQuestionsScore e : eqsList) {
//                Question question = e.getQuestion();
//                if (question.isMultipleChoice()){
//                    e.setQuestion(((MultipleChoiceQuestion) e.getQuestion()));
//                }else {
//                    e.setQuestion(((DescriptiveQuestion) e.getQuestion()));
//                }
//            }
            for (ExamQuestionsScore eqs : eqsList) {
                Answer answer = new Answer(null, null, 0, collegian, eqs);
                Answer persisted = answerService.saveAnswer(answer);
                answers.add(persisted);
            }
//            ExamQuestionsScore eqs = null;
//            for (int i = 0; i < eqsList.size(); i++) {
//                eqs = eqsList.get(i);
//                Answer answer = new Answer(null, null, 0, collegian, eqs);
//                Answer persisted = answerService.saveAnswer(answer);
//                answers.add(persisted);
//            }
            request.getSession().setAttribute("isTheFirstTime", false);
            request.getSession().setAttribute("answers", answers);
        }

//        request.getSession().setAttribute("isTheFirstTime", false);
        Answer answer = answers.get(order - 1);

        if (answer.getExamQuestionsScore().getQuestion().isMultipleChoice()) {
            List<QuestionOption> options = optionService
                    .findAllByQuestionId(answer.getExamQuestionsScore().getQuestion().getId());
            model.addAttribute("options", options);
        }

        model.addAttribute("answer", answer);
//        Date date = Calendar.getInstance().getTime();
//        Long finish = System.currentTimeMillis() + ((long)exam.getDuration()*60_000);
//        request.getSession().setAttribute("examStarted", date.getTime());
//        request.getSession().setAttribute("examStarted", protocol.getStart().getTime());
//        final int remaining = getRemainingTime(request);
//        model.addAttribute("examTime", remaining);

        return "startExam";
    }

    @GetMapping("/answer-next")
    public String saveAnswerAndGoToNextQuestion(@ModelAttribute Answer answer,
                                                Model model,
                                                HttpServletRequest request) {
        List<Answer> answers = (List<Answer>) request.getSession().getAttribute("answers");

        int order = (int) request.getSession().getAttribute("order");
        Answer persistedAnswer = answerService.saveAnswer(answer);

        //replace the new answer with the previous one
        answers.set((order - 1), persistedAnswer);
        request.getSession().setAttribute("answers", answers);

        Exam exam = answer.getExamQuestionsScore().getExam();
        Collegian collegian = answer.getCollegian();
        order++;
        request.getSession().setAttribute("order", order);
//        model.addAttribute("persistedAnswer", persistedAnswer);
        String redirect = "redirect:/collegian/participate-exam/" + exam.getId() + "/" + collegian.getId();
        return redirect;
    }

    @GetMapping("/answer-previous")
    public String saveAnswerAndGoToPreviousQuestion(@ModelAttribute Answer answer,
                                                    Model model,
                                                    HttpServletRequest request) {
        List<Answer> answers = (List<Answer>) request.getSession().getAttribute("answers");

        int order = (int) request.getSession().getAttribute("order");
        Answer persistedAnswer = answerService.saveAnswer(answer);

        //replace the new answer with the previous one
        answers.set((order - 1), persistedAnswer);
        request.getSession().setAttribute("answers", answers);

        Exam exam = answer.getExamQuestionsScore().getExam();
        Collegian collegian = answer.getCollegian();
        order--;
        request.getSession().setAttribute("order", order);
        String redirect = "redirect:/collegian/participate-exam/" + exam.getId() + "/" + collegian.getId();
        return redirect;
    }

    @GetMapping("/answer-submit")
    public String submitAnswers(@ModelAttribute Answer answer) {
        answerService.saveAnswer(answer);
        return "answersRegistered";
    }
//    @RequestMapping(value = "/time")
//    @ResponseBody
//    public Integer timer(HttpServletRequest request) {
//        return getRemainingTime(request);
//    }

//    private int getRemainingTime(HttpServletRequest request) {
//        final long start = (long) request.getSession().getAttribute("examStarted");
//        final int remaining = (int) ((examTimeMins * 60) - ((Calendar.getInstance().getTimeInMillis() - start) / 1000));
//        return remaining;
//    }

}
