//package com.shervin.maktabfinalproject.models;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.util.Objects;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Embeddable
//public class ExamQuestionsScoreId implements Serializable {
//    private Long exam;
//    private Long question;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ExamQuestionsScoreId that = (ExamQuestionsScoreId) o;
//        return Objects.equals(exam, that.exam) &&
//                Objects.equals(question, that.question);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(exam, question);
//    }
//}
