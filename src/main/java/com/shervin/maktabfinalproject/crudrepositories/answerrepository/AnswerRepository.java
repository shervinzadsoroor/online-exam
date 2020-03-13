package com.shervin.maktabfinalproject.crudrepositories.answerrepository;

import com.shervin.maktabfinalproject.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByCollegian_Id(Long id);
}
