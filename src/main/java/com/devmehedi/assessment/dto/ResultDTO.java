package com.devmehedi.assessment.dto;

import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.User;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ResultDTO {
    private Long id;
    private String resultIdentifier;
    private double markGot;
    private int attempted;
    private int correctAnswer;
    private User user;
    private Assessment assessment;
}
