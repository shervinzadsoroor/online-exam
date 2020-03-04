package com.shervin.maktabfinalproject.crudrepositories.multiplechoicequestionrepository;

import com.shervin.maktabfinalproject.models.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Long> {
}
