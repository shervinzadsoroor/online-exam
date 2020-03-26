package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.answerrepository.AnswerService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.crudrepositories.examrepository.ExamService;
import com.shervin.maktabfinalproject.crudrepositories.optionrepository.OptionService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequestMapping("/collegian")
@Controller
public class CollegianController {
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

    //the timer thread use this property to access the answer object
    private int remainingTimeInSeconds;
    private Answer ANSWER;

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
        account.setLastLoginDate(new Date());
        account = accountService.saveAccount(account);

        if (account.getStatus().equalsIgnoreCase("not registered")) {

            Collegian collegian = new Collegian();
            collegian.setAccount(account);

            model.addAttribute("collegian", collegian);

            return "registrationFormForCollegian";

        } else if (account.getStatus().equalsIgnoreCase("waiting")) {
            model.addAttribute("account", account);
            return "registrationWaitingDetails";
        }
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
        request.getSession().setAttribute("remainingTime", remainingTimeInSeconds);


        List<Exam> examList = examService.findAllExamsByCourseId(courseId);
        Collegian collegian = collegianService.findById(collegianId);

        model.addAttribute("course", courseService.findById(courseId));
        model.addAttribute("collegian", collegian);

        //if the exam is been participated by collegian, the boolean isBeenParticipated will set to true
        for (Exam exam : examList) {
            for (Exam participatedExam : collegian.getParticipatedExams()) {
                //we use equals instead of '==' because the type of id is wrapper, not primitive (Long)
                if (exam.getId().equals(participatedExam.getId())) {
                    exam.setBeenParticipated(true);
                }
            }
            List<Answer> answer_list = answerService
                    .findAllAnswersByCollegianIdAndExamId(collegianId, exam.getId());
            if (!answer_list.isEmpty()) {
                if (!answer_list.get(0).isTimeout()) {
                    exam.setRunning(true);
                    request.getSession().setAttribute("isTheFirstTime", false);
                    request.getSession().setAttribute("answers", answer_list);
                }
            }
        }
        model.addAttribute("exams", examList);

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

        //application enters the 'if scope' only for the first time ---------------------
        if (isTheFirstTime) {

            List<ExamQuestionsScore> eqsList = exam.getExamQuestionsScores();
            int numberOfQuestions = eqsList.size();
            request.getSession().setAttribute("numOfQuestions", numberOfQuestions);

            for (ExamQuestionsScore eqs : eqsList) {
                Answer answer = new Answer(null, null, 0, false, false, collegian, eqs);
                Answer persisted = answerService.saveAnswer(answer);
                answers.add(persisted);
            }
            request.getSession().setAttribute("isTheFirstTime", false);
            request.getSession().setAttribute("answers", answers);

            setParticipatedCollegiansOfExam(exam, collegian);

            //--------------------------------------------------------------------

            //starts a countdown timer in a parallel thread
            final Timer timer = new Timer();
            //the 'run' method use this list for changing the value of 'isTimeout' attribute using native query
            List<Long> answerId_list = new ArrayList<>();

            for (Answer answer : answers) {
                answerId_list.add(answer.getId());
            }

            timer.scheduleAtFixedRate(new TimerTask() {

                int remainingTime = (int) exam.getDuration() * 60; //converting minute to seconds
                List<Long> answerIds = answerId_list;

                public void run() {
                    remainingTime--;
                    //updating the remainingTimeInSeconds every second
                    remainingTimeInSeconds = remainingTime;
                    if (remainingTime < 0) {
                        timer.cancel();
                        answerService.setIsTimeoutToTrueValue(answerIds);
                        examService.calculateExamResult(answers);
                    }
                }
            }, 0, 1000);
        }
        //---------------------------------------------------------------------------

        Answer answer = answerService.findById(answers.get(order - 1).getId());
        //timer thread can access this 'ANSWER' object
        ANSWER = answer;

        if (answer.getExamQuestionsScore().getQuestion().isMultipleChoice()) {
            List<QuestionOption> options = optionService
                    .findAllByQuestionId(answer.getExamQuestionsScore().getQuestion().getId());
            model.addAttribute("options", options);
        }

        request.getSession().setAttribute("remainingTime", remainingTimeInSeconds);
        model.addAttribute("answer", answer);

        return "startExam";
    }


    @GetMapping("/answer/{step}")
    public String saveAnswerAndGoToPreviousOrNextQuestion(@PathVariable("step") String step,
                                                          @ModelAttribute Answer answer,
                                                          Model model,
                                                          HttpServletRequest request) {
        List<Answer> answers = (List<Answer>) request.getSession().getAttribute("answers");

        //we fetch answer again because one of its properties may be change in database by timer
        Answer answer1 = answerService.findById(answer.getId());
        answer1.setContent(answer.getContent());

        int order = (int) request.getSession().getAttribute("order");
        Answer persistedAnswer = answerService.saveAnswer(answer1);

        //replace the new answer with the previous one
        answers.set((order - 1), persistedAnswer);
        request.getSession().setAttribute("answers", answers);

        Exam exam = answer.getExamQuestionsScore().getExam();
        Collegian collegian = answer.getCollegian();
        if (step.equals("next")) {
            order++;
        } else if (step.equals("previous")) {
            order--;
        }
        request.getSession().setAttribute("order", order);
        String redirect = "redirect:/collegian/participate-exam/" + exam.getId() + "/" + collegian.getId();
        return redirect;
    }

    @GetMapping("/answer-submit")
    public String submitAnswers(@ModelAttribute Answer answer,
                                HttpServletRequest request) {
        //we fetch answer again because one of its properties may be change in database by timer
        Answer answer1 = answerService.findById(answer.getId());
        answer1.setContent(answer.getContent());

        int order = (int) request.getSession().getAttribute("order");
        Exam exam = answer1.getExamQuestionsScore().getExam();
        Answer persistedAnswer = answerService.saveAnswer(answer1);

        List<Answer> answers = (List<Answer>) request.getSession().getAttribute("answers");
        answers.set((order - 1), persistedAnswer);

        //set isTimeout of all answers to true------------------
        String username = (String) request.getSession().getAttribute("username");
        Collegian collegian = collegianService.findCollegianByAccountUsername(username);

        // assign score to optional questions automatically
        examService.calculateExamResult(answers);
        List<Long> answerIds = new ArrayList<>();
        for (Answer a : answers) {
            answerIds.add(a.getId());
        }
        System.out.println("answerIds = " + answerIds);
        answerService.setIsTimeoutToTrueValue(answerIds);
        //------------------------------------------------------

        return "answersRegistered";
    }

    public void setParticipatedCollegiansOfExam(Exam exam, Collegian collegian) {
        List<Collegian> collegians = exam.getParticipatedCollegians();
        collegians.add(collegian);
        exam.setParticipatedCollegians(collegians);
        examService.saveExam(exam);
    }

    @GetMapping("/exam-result/{examId}/{collegianId}")
    public String showExamResultToCollegian(@PathVariable("examId") Long examId,
                                            @PathVariable("collegianId") Long collegianId,
                                            Model model) {

        Collegian collegian = collegianService.findById(collegianId);
        Exam exam = examService.findById(examId);
        double sum = 0;
        List<Answer> answers = answerService.findAllAnswersByCollegianIdAndExamId(collegianId, examId);
        for (Answer answer : answers) {
            sum += answer.getGrade();
        }
        model.addAttribute("totalScore", sum);
        model.addAttribute("answers", answers);

        return "examResultForCollegian";
    }

}
