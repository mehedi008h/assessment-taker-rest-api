package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.dto.AssessmentDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.AssessmentService;
import com.devmehedi.assessment.service.QuestionService;
import com.devmehedi.assessment.service.ValidationErrorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/question")
@RestController
public class QuestionController {
    private QuestionService questionService;
    private AssessmentService assessmentService;
    private ValidationErrorService validationErrorService;

    @Autowired
    public QuestionController(QuestionService questionService, AssessmentService assessmentService, ValidationErrorService validationErrorService) {
        this.questionService = questionService;
        this.assessmentService = assessmentService;
        this.validationErrorService = validationErrorService;
    }

    // create question
    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody AssessmentDTO assessmentDTO, BindingResult result) throws NotFoundException {
        // validate field error
        ResponseEntity<?> errorMap = validationErrorService.ValidationService(result);
        if (errorMap != null) return response(BAD_REQUEST, errorMap.getBody().toString());

        AssessmentDTO newAssessment = assessmentService.addAssessment(assessmentDTO);
        return new ResponseEntity<>(newAssessment, OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
