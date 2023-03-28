package com.devmehedi.assessment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDTO {
    private Long id;
    private String questionIdentifier;
    private String content;
    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;
    private String answer;
    private String givenAnswer;
    private AssessmentDTO assessment;
}
