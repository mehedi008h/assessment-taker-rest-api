package com.devmehedi.assessment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "category")
@Entity(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(updatable = false, nullable = false, name = "category_identifier")
    private String categoryIdentifier;
    @NotBlank(message = "Category title is a required!")
    private String title;
    @NotBlank(message = "Category description is a required!")
    @Column(length = 5000)
    private String description;
}
