package com.devmehedi.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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

    // relation mapping
    // one-to-many relation between quiz
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            mappedBy = "category",
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Assessment> assessments = new LinkedHashSet<>();
}
