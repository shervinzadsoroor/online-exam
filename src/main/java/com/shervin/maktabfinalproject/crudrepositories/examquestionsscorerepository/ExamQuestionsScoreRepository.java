package com.shervin.maktabfinalproject.crudrepositories.examquestionsscorerepository;

import com.shervin.maktabfinalproject.models.ExamQuestionsScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamQuestionsScoreRepository extends JpaRepository<ExamQuestionsScore, Long> {

    List<ExamQuestionsScore> findAllByExam_Id(Long examId);

    List<ExamQuestionsScore> findAllByQuestion_Id(Long questionId);
}
