package com.shervin.maktabfinalproject.crudrepositories.optionrepository;

import com.shervin.maktabfinalproject.models.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<QuestionOption, Long> {
}
