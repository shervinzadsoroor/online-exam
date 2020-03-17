package com.shervin.maktabfinalproject.crudrepositories.answerrepository;

import com.shervin.maktabfinalproject.models.Answer;
import com.shervin.maktabfinalproject.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByCollegian_Id(Long id);

    List<Answer> findAllByCollegian_IdAndExamQuestionsScore_Exam_id(Long collegianId, Long examId);
}
