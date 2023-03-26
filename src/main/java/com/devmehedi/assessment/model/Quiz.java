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
@Table(name = "quiz")
@Entity(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(updatable = false, nullable = false, name = "quiz_identifier")
    private String quizIdentifier;
    @NotBlank(message = "Quiz title is a required!")
    private String title;
    @NotBlank(message = "Quiz description is a required!")
    @Column(length = 5000)
    private String description;
    @NotBlank(message = "Quiz time is a required!")
    private String time;
    private int attempt;
    @Column(name = "image_url")
    private String imageUrl;
    private boolean active = false;
    private int totalTaken;
}
