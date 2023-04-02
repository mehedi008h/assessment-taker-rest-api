package com.devmehedi.assessment.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssessmentDTO {
    private Long id;
    private String assessmentIdentifier;
    @NotBlank(message = "Assessment title is a required!")
    private String title;
    @NotBlank(message = "Assessment description is a required!")
    @Column(length = 5000)
    private String description;
    @NotBlank(message = "Assessment time is a required!")
    private String time;
    private int attempt;
    private String imageUrl;
    private boolean active;
    private int totalTaken;
    private CategoryDTO category;
}
