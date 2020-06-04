package com.shervin.maktabfinalproject.crudrepositories.descriptivequestionrepository;

import com.shervin.maktabfinalproject.models.DescriptiveQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptiveQuestionService {
    private final DescriptiveQuestionRepository descriptiveQuestionRepository;

    @Autowired
    public DescriptiveQuestionService(DescriptiveQuestionRepository descriptiveQuestionRepository) {
        this.descriptiveQuestionRepository = descriptiveQuestionRepository;
    }

    public DescriptiveQuestion save(DescriptiveQuestion question) {
        return descriptiveQuestionRepository.save(question);
    }

    public DescriptiveQuestion findById(Long id) {
        return descriptiveQuestionRepository.findById(id).get();
    }
}
