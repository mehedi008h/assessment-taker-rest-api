package com.devmehedi.assessment.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    private Long id;
    private String categoryIdentifier;
    private String title;
    private String description;
}
