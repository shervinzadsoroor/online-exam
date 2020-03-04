package com.shervin.maktabfinalproject.crudrepositories.descriptivequestionrepository;

import com.shervin.maktabfinalproject.models.DescriptiveQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptiveQuestionRepository extends JpaRepository<DescriptiveQuestion, Long> {
}
