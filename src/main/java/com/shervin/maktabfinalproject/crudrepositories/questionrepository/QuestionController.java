package com.shervin.maktabfinalproject.crudrepositories.questionrepository;

import com.shervin.maktabfinalproject.crudrepositories.descriptivequestionrepository.DescriptiveQuestionService;
import com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository.ExamQuestionsScoreService;
import com.shervin.maktabfinalproject.crudrepositories.examrepository.ExamService;
import com.shervin.maktabfinalproject.crudrepositories.multiplechoicequestionrepository.MultipleChoiceQuestionService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/list/{id}")
    public String showAllQuestionsOfInstructor(@PathVariable("id") Long examId, Model model) {
        Exam exam = examService.findById(examId);
        List<Question> questions = questionService.findAllQuestionsByInstructor(exam.getInstructor().getId());

        List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            ExamQuestionsScore examQuestionsScore = new ExamQuestionsScore();
//
//            ExamQuestionsScoreId examQuestionsScoreId = new ExamQuestionsScoreId(examId, questions.get(i).getId());
//            examQuestionsScore.setExamQuestionsScoreId(examQuestionsScoreId);

            examQuestionsScore.setExam(exam);
            examQuestionsScore.setQuestion(questions.get(i));
            examQuestionsScores.add(examQuestionsScore);
        }

//        model.addAttribute("examQuestionsScores", examQuestionsScores);

        model.addAttribute("questions", questionService.findAllQuestions());
        model.addAttribute("exam", exam);
        return "addQuestionsToExamFromBank";
    }

    @PostMapping("/addToExam")
    public String addQuestionsToExam(@ModelAttribute Exam exam) {
        System.out.println("sddddddddddddddddddddddddddddddddddddddddd");
        System.out.println("exam.getExamQuestionsScores() = " + exam.getExamQuestionsScores());
        examService.saveExam(exam);
        return null;
    }

    @GetMapping("/create/multipleChoice/{id}")
    public String sendCreateMultipleChoiceQuestionForm(@PathVariable("id") Long examId, Model model) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();

        Exam exam = examService.findById(examId);

        ExamQuestionsScore examQuestionsScore = new ExamQuestionsScore();
        examQuestionsScore.setExam(exam);

        List<ExamQuestionsScore> examQuestionsScores = new ArrayList<>();
        examQuestionsScores.add(examQuestionsScore);

        multipleChoiceQuestion.setExamQuestionsScores(examQuestionsScores);
        multipleChoiceQuestion.setInstructor(exam.getInstructor());
        multipleChoiceQuestion.setMultipleChoice(true);

        model.addAttribute("multipleChoiceQuestion", multipleChoiceQuestion);
        return "createMultipleChoiceQuestion";
    }

    @PostMapping("/create/multipleChoice")
    public String createMultipleChoiceQuestion(@ModelAttribute MultipleChoiceQuestion question) {

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestionService.save(question);

        List<ExamQuestionsScore> examQuestionsScores = multipleChoiceQuestion.getExamQuestionsScores();

        ExamQuestionsScore examQuestionsScore = examQuestionsScores.get(0);

        examQuestionsScore.setQuestion(multipleChoiceQuestion);

        ExamQuestionsScore persistedExamQuestionsScore = examQuestionsScoreService.save(examQuestionsScore);
        examQuestionsScores.remove(0);
        examQuestionsScores.add(persistedExamQuestionsScore);

        multipleChoiceQuestion.setExamQuestionsScores(examQuestionsScores);
        multipleChoiceQuestionService.save(multipleChoiceQuestion);

        return null;
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
    public String createDescriptiveQuestion(@ModelAttribute DescriptiveQuestion question) {

        DescriptiveQuestion descriptiveQuestion = descriptiveQuestionService.save(question);

        List<ExamQuestionsScore> examQuestionsScores = descriptiveQuestion.getExamQuestionsScores();

        ExamQuestionsScore examQuestionsScore = examQuestionsScores.get(0);

        examQuestionsScore.setQuestion(descriptiveQuestion);

        ExamQuestionsScore persistedExamQuestionsScore = examQuestionsScoreService.save(examQuestionsScore);
        examQuestionsScores.remove(0);
        examQuestionsScores.add(persistedExamQuestionsScore);

        descriptiveQuestion.setExamQuestionsScores(examQuestionsScores);
        descriptiveQuestionService.save(descriptiveQuestion);

        return null;
    }

}
