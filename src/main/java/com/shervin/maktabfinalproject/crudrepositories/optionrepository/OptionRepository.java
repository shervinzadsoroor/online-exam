package com.shervin.maktabfinalproject.crudrepositories.optionrepository;

import com.shervin.maktabfinalproject.models.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findAllByMultipleChoiceQuestion_id(Long id);
}
