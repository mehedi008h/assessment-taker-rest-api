package com.devmehedi.assessment.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDTO {
    private Long id;
    private String questionIdentifier;
    @Column( length = 5000)
    @NotBlank(message = "Question content is required!")
    private String content;
    private String image;
    @NotBlank(message = "Question option1 is required!")
    private String option1;
    @NotBlank(message = "Question option2 is required!")
    private String option2;
    @NotBlank(message = "Question option3 is required!")
    private String option3;
    @NotBlank(message = "Question option4 is required!")
    private String option4;
    private String option5;
    @NotBlank(message = "Question answer is required!")
    private String answer;
    private String givenAnswer;
    private AssessmentDTO assessment;
}
