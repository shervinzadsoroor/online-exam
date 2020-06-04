package com.shervin.maktabfinalproject.crudrepositories.answerrepository;

import com.shervin.maktabfinalproject.models.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId).get();
    }

    public List<Answer> findAllAnswers() {
        return answerRepository.findAll();
    }

    public List<Answer> findAllAnswersByCollegianId(Long collegianId) {
        return answerRepository.findAllByCollegian_Id(collegianId);
    }

    public List<Answer> findAllAnswersByCollegianIdAndExamId(Long collegianId, Long examId) {
        return answerRepository.findAllByCollegian_IdAndExamQuestionsScore_Exam_id(collegianId, examId);
    }

    public void setIsTimeoutToTrueValue(List<Long> answerIds){
        for (Long id : answerIds) {
            answerRepository.setIsTimeoutToTrue(true, id);
        }
    }
}
