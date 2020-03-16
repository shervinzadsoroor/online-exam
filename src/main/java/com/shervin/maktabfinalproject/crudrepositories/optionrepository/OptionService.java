package com.shervin.maktabfinalproject.crudrepositories.optionrepository;

import com.shervin.maktabfinalproject.models.QuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    private OptionRepository optionRepository;

    public QuestionOption save(QuestionOption option) {
        return optionRepository.save(option);
    }

    public QuestionOption findById(Long id){
        return optionRepository.findById(id).get();
    }

    public List<QuestionOption> findAllByQuestionId(Long id){
        return optionRepository.findAllByMultipleChoiceQuestion_id(id);
    }

}
