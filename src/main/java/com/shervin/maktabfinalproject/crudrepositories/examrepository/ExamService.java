package com.shervin.maktabfinalproject.crudrepositories.examrepository;

import com.shervin.maktabfinalproject.models.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> findAllExamsByCourseId(Long id) {
        return examRepository.findAllByCourse_Id(id);
    }

    public Exam findById(Long id) {
        return examRepository.findById(id).get();
    }
}
