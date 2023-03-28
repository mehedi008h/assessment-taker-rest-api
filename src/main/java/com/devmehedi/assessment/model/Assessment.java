package com.devmehedi.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "assessment")
@Entity(name = "Assessment")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(updatable = false, nullable = false, name = "assessment_identifier")
    private String assessmentIdentifier;
    @NotBlank(message = "Assessment title is a required!")
    private String title;
    @NotBlank(message = "Assessment description is a required!")
    @Column(length = 5000)
    private String description;
    @NotBlank(message = "Assessment time is a required!")
    private String time;
    private int attempt;
    @Column(name = "image_url")
    private String imageUrl;
    private boolean active = false;
    private int totalTaken;

    // relation mapping
    // many-to-one relation between category
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;

    // one-to-many relation between question
    @OneToMany(
            mappedBy = "assessment",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();
}
