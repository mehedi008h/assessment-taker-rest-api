package com.devmehedi.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "result")
@Entity(name = "Result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(name = "result_identifier",nullable = false, updatable = false)
    private String resultIdentifier;
    @Column(name = "mark_got")
    private double markGot;
    private int attempted;
    @Column(name = "correct_answer")
    private int correctAnswer;

    // relation mapping
    // many-to-one relation between user
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // relation mapping
    // many-to-one relation between assessment
    @ManyToOne(fetch = FetchType.LAZY)
    private Assessment assessment;

    // one-to-many relation between answer
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            mappedBy = "result",
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

}
