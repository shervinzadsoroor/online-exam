package com.shervin.maktabfinalproject.crudrepositories.multiplechoicequestionrepository;

import com.shervin.maktabfinalproject.models.MultipleChoiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultipleChoiceQuestionService {
    private final MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Autowired
    public MultipleChoiceQuestionService(MultipleChoiceQuestionRepository multipleChoiceQuestionRepository) {
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
    }

    public MultipleChoiceQuestion save(MultipleChoiceQuestion question) {
        return multipleChoiceQuestionRepository.save(question);
    }

    public MultipleChoiceQuestion findById(Long id) {
        return multipleChoiceQuestionRepository.findById(id).get();
    }
}
