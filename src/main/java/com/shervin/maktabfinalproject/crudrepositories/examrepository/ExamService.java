package com.shervin.maktabfinalproject.crudrepositories.examrepository;

import com.shervin.maktabfinalproject.crudrepositories.answerrepository.AnswerService;
import com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository.ExamQuestionsScoreService;
import com.shervin.maktabfinalproject.models.Answer;
import com.shervin.maktabfinalproject.models.Exam;
import com.shervin.maktabfinalproject.models.MultipleChoiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final ExamQuestionsScoreService examQuestionsScoreService;
    private final AnswerService answerService;

    @Autowired
    public ExamService(ExamRepository examRepository, ExamQuestionsScoreService examQuestionsScoreService, AnswerService answerService) {
        this.examRepository = examRepository;
        this.examQuestionsScoreService = examQuestionsScoreService;
        this.answerService = answerService;
    }

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> findAllExamsByCourseId(Long id) {
        return examRepository.findAllByCourse_IdOrderByIdDesc(id);
    }

    public Exam findById(Long id) {
        return examRepository.findById(id).get();
    }

    public void deleteExamById(Long id) {
        examRepository.deleteById(id);
    }

    public void calculateExamResult(List<Answer> answers) {

        for (Answer answer : answers) {

            //if the question is multipleChoice
            if (answer.getExamQuestionsScore().getQuestion().isMultipleChoice()) {
                //cast the question to multipleChoice question to access its fields
                MultipleChoiceQuestion optionalQuestion =
                        (MultipleChoiceQuestion) answer.getExamQuestionsScore().getQuestion();

                //if the answer is true it gives total score to the answer
                if (optionalQuestion.getAnswerKey().equals(answer.getContent())) {
                    answer.setGrade(answer.getExamQuestionsScore().getScore());
                }
                answer.setCorrected(true);
                answerService.saveAnswer(answer);
            }
        }
    }

}


