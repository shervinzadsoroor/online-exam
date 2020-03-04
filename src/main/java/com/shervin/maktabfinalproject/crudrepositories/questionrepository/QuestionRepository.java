package com.shervin.maktabfinalproject.crudrepositories.questionrepository;

import com.shervin.maktabfinalproject.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
//    List<Question> findAllByExamQuestionsScores();

//    List<Question> findAllByExamInstructor_Id(Long instructorId);

    List<Question> findAllByInstructor_Id(Long instructorId);
}
