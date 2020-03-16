package com.shervin.maktabfinalproject.crudrepositories.questionrepository;

import com.shervin.maktabfinalproject.crudrepositories.descriptivequestionrepository.DescriptiveQuestionService;
import com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository.ExamQuestionsScoreService;
import com.shervin.maktabfinalproject.crudrepositories.examrepository.ExamService;
import com.shervin.maktabfinalproject.crudrepositories.multiplechoicequestionrepository.MultipleChoiceQuestionService;
import com.shervin.maktabfinalproject.crudrepositories.optionrepository.OptionService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RequestMapping("/question")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private MultipleChoiceQuestionService multipleChoiceQuestionService;
    @Autowired
    private DescriptiveQuestionService descriptiveQuestionService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamQuestionsScoreService examQuestionsScoreService;
    @Autowired
    private OptionService optionService;

    @GetMapping("/list/{id}")
    public String showAllQuestionsOfInstructor(@PathVariable("id") Long examId, Model model) {
        Exam exam = examService.findById(examId);
        List<Question> questions = questionService.findAllQuestionsByInstructor(exam.getInstructor().getId());

        Course course = new Course();
        course.setExams(Arrays.asList(examService.findById(examId)));

        model.addAttribute("course", course);

        model.addAttribute("questions", questions);

        return "addQuestionsToExamFromBank";
    }

    /**
     * a trick is used here! a list of chosen questions from question bank
     * required , but each exam has the list of examQuestionScore not questions,
     * so i have used a course object to do this. naturally when we get the list of questions,
     * the course will not be persist and will be omitted automatically,
     * this course is created in @GetMapping("/list/{id}") and is caught
     * in @PostMapping("/addToExam") APIs
     */
    @PostMapping("/addToExam")
    public String addQuestionsToExam(@ModelAttribute Course course) {

        Exam exam = course.getExams().get(0);
        List<ExamQuestionsScore> examQuestionsScores = exam.getExamQuestionsScores();
        System.out.println("course.getQuestions() = " + course.getQuestions());
        System.out.println("exam.getExamQuestionsScores() = " + exam.getExamQuestionsScores());
        for (Question question : course.getQuestions()) {
            boolean isExamHasThisQuestion = false;

            // we use equals for compare ids because the have been created
            // with wrapper class(Long) instead of primitive type(long)
            for (ExamQuestionsScore eqs : exam.getExamQuestionsScores()) {
                if (question.getId().equals(eqs.getQuestion().getId())) {
                    isExamHasThisQuestion = true;
                }
            }
            if (!isExamHasThisQuestion) {
                ExamQuestionsScore examQuestionsScore = new ExamQuestionsScore();
                examQuestionsScore.setQuestion(question);
                examQuestionsScore.setExam(exam);
                examQuestionsScoreService.save(examQuestionsScore);
            }
        }

        return null;
    }

    @GetMapping("/create/multipleChoice/{id}")
    public String sendCreateMultipleChoiceQuestionForm(@PathVariable("id") Long examId,
                                                       Model model,
                                                       HttpServletRequest request) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();

//        MultipleChoiceQuestion persistedQuestion =
//                multipleChoiceQuestionService.save(new MultipleChoiceQuestion());

        Exam exam = examService.findById(examId);

        ExamQuestionsScore examQuestionsScore = new ExamQuestionsScore();
        examQuestionsScore.setExam(exam);
//        examQuestionsScore.setQuestion(persistedQuestion);
//        ExamQuestionsScore persistedEQS = examQuestionsScoreService.save(examQuestionsScore);

//        List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();
//        examQuestionsScores.add(persistedEQS);

//        persistedQuestion.setExamQuestionsScores(examQuestionsScores);
        multipleChoiceQuestion.setInstructor(exam.getInstructor());
        multipleChoiceQuestion.setMultipleChoice(true);

        List<QuestionOption> options = new ArrayList<>();
        // TODO: 3/14/20 options quantity is hard-coded but must be dynamical and this is a only for test
        for (int i = 1; i <= 3; i++) {
            QuestionOption option = new QuestionOption(null, null,
                    false, (i), null);
//            QuestionOption persistedOption = optionService.save(option);
            options.add(option);
        }
        multipleChoiceQuestion.setOptions(options);

        request.getSession().setAttribute("exam", exam);
//        request.getSession().setAttribute("options", options);
        model.addAttribute("multipleChoiceQuestion", multipleChoiceQuestion);
//        model.addAttribute("options", options);
        return "createMultipleChoiceQuestion";
    }

    @PostMapping("/create/multipleChoice")
    public String createMultipleChoiceQuestion(@ModelAttribute MultipleChoiceQuestion question,
                                               HttpServletRequest request) {

        Exam exam = (Exam) request.getSession().getAttribute("exam");
        MultipleChoiceQuestion persistedQuestion = multipleChoiceQuestionService.save(question);
        for (QuestionOption option : question.getOptions()) {
            option.setMultipleChoiceQuestion(persistedQuestion);
            optionService.save(option);
        }

        examQuestionsScoreService
                .save(new ExamQuestionsScore(null,exam,persistedQuestion,null,0));

//        persistedQuestion.setExamQuestionsScores();
//        Scanner scanner = new Scanner(System.in);
//        int n  = scanner.nextInt();


        return "redirect:/exam/edit/"+exam.getId();
    }

    @GetMapping("/create/descriptive/{id}")
    public String sendCreateDescriptiveQuestionForm(@PathVariable("id") Long examId, Model model) {
        DescriptiveQuestion descriptiveQuestion = new DescriptiveQuestion();

        Exam exam = examService.findById(examId);

        ExamQuestionsScore examQuestionsScore = new ExamQuestionsScore();
        examQuestionsScore.setExam(exam);

        List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();
        examQuestionsScores.add(examQuestionsScore);

        descriptiveQuestion.setExamQuestionsScores(examQuestionsScores);
        descriptiveQuestion.setInstructor(exam.getInstructor());
        descriptiveQuestion.setMultipleChoice(false);

        model.addAttribute("descriptiveQuestion", descriptiveQuestion);
        return "createDescriptiveQuestion";
    }

    @PostMapping("/create/descriptive")
    public String createDescriptiveQuestion(@ModelAttribute DescriptiveQuestion question,
                                            HttpServletRequest request) {

        DescriptiveQuestion descriptiveQuestion = descriptiveQuestionService.save(question);

        List<ExamQuestionsScore> examQuestionsScores = descriptiveQuestion.getExamQuestionsScores();

        ExamQuestionsScore examQuestionsScore = examQuestionsScores.get(0);

        examQuestionsScore.setQuestion(descriptiveQuestion);

        ExamQuestionsScore persistedExamQuestionsScore = examQuestionsScoreService.save(examQuestionsScore);
        examQuestionsScores.remove(0);
        examQuestionsScores.add(persistedExamQuestionsScore);

        descriptiveQuestion.setExamQuestionsScores(examQuestionsScores);
        descriptiveQuestionService.save(descriptiveQuestion);

        Exam exam = (Exam) request.getSession().getAttribute("exam");
        return "redirect:/exam/edit/"+exam.getId();
    }

}
