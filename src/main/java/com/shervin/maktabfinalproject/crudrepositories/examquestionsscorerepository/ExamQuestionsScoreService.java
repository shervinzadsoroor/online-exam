package com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository;

import com.shervin.maktabfinalproject.models.Exam;
import com.shervin.maktabfinalproject.models.ExamQuestionsScore;
import com.shervin.maktabfinalproject.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamQuestionsScoreService {
    @Autowired
    private ExamQuestionsScoreRepository examQuestionsScoreRepository;

    public ExamQuestionsScore save(ExamQuestionsScore examQuestionsScore) {
        return examQuestionsScoreRepository.save(examQuestionsScore);
    }

    public List<ExamQuestionsScore> findAllByExamId(Long examId) {
        return examQuestionsScoreRepository.findAllByExam_Id(examId);
    }

    public List<ExamQuestionsScore> findAllByQuestionId(Long questionId) {
        return examQuestionsScoreRepository.findAllByQuestion_Id(questionId);
    }

    public ExamQuestionsScore findByExamAndQuestion(Exam exam, Question question) {
        return examQuestionsScoreRepository.findByExam_idAndQuestion_id(exam.getId(), question.getId());
    }
}
