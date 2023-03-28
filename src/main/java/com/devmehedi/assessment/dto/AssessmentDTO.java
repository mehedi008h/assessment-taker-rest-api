package com.devmehedi.assessment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssessmentDTO {
    private Long id;
    private String assessmentIdentifier;
    private String title;
    private String description;
    private String time;
    private int attempt;
    private String imageUrl;
    private boolean active;
    private int totalTaken;
    private CategoryDTO category;
}
