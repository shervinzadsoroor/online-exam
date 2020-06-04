package com.shervin.maktabfinalproject.crudrepositories.questionrepository;

import com.shervin.maktabfinalproject.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).get();
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

//    public List<Question> findAllQuestionsByExam(Long examId){
//        return questionRepository.findAllByExam_Id(examId);
//    }

    public List<Question> findAllQuestionsByInstructor(Long instructorId) {
        return questionRepository.findAllByInstructor_Id(instructorId);
    }
}
