package com.shervin.maktabfinalproject.crudrepositories.examrepository;

import com.shervin.maktabfinalproject.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findAllByCourse_Id(Long id);
}
