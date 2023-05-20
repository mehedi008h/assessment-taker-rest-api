package com.devmehedi.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "answer")
@Entity(name = "Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false, updatable = false)
    private String answerIdentifier;
    private String givenAnswer;
    // one-to-one relation between question
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Question question ;
    // many-to-one relation between result
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Result result;
}
