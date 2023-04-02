package com.devmehedi.assessment.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    private Long id;
    private String categoryIdentifier;
    @NotBlank(message = "Category title is a required!")
    private String title;
    @NotBlank(message = "Category description is a required!")
    @Column(length = 5000)
    private String description;
}
