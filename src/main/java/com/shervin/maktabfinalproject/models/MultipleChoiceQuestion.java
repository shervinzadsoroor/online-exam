package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MultipleChoiceQuestion extends Question {

    private String answerKey;
//    final private boolean isMultipleChoice = true;

}
