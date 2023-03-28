package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.exception.ExceptionHandling;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.AssessmentService;
import com.devmehedi.assessment.service.ValidationErrorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/assessment")
@RestController
public class AssessmentController extends ExceptionHandling {
    private static final String ASSESSMENT_DELETED_SUCCESSFULLY = "Assessment Deleted Successfully";
    private AssessmentService assessmentService;
    private ValidationErrorService validationErrorService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, ValidationErrorService validationErrorService) {
        this.assessmentService = assessmentService;
        this.validationErrorService = validationErrorService;
    }

    // create assessment
    @PostMapping
    public ResponseEntity<?> createAssessment(@Valid @RequestBody Assessment assessment, BindingResult result) throws NotFoundException {
        // validate field error
        ResponseEntity<?> errorMap = validationErrorService.ValidationService(result);
        if (errorMap != null) return response(BAD_REQUEST, errorMap.getBody().toString());

        Assessment newAssessment = assessmentService.addAssessment(assessment);
        return new ResponseEntity<>(newAssessment, OK);
    }

    // get all assessment
    @GetMapping
    public ResponseEntity<Set<Assessment>> getAllCategory() {
        Set<Assessment> assessments = assessmentService.getAssessments();
        return new ResponseEntity<>(assessments, OK);
    }

    // get single assessment by assessment identifier
    @GetMapping("/{assessmentIdentifier}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable("assessmentIdentifier") String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = assessmentService.getAssessment(assessmentIdentifier);
        return new ResponseEntity<>(assessment, OK);
    }

    // update assessment by assessment identifier
    @PutMapping
    public ResponseEntity<Assessment> updateAssessment(@RequestBody Assessment assessment) throws NotFoundException {
        Assessment updateAssessment = assessmentService.updateAssessment(assessment);
        return new ResponseEntity<>(updateAssessment, OK);
    }

    // delete assessment by assessment identifier
    @DeleteMapping("/{assessmentIdentifier}")
    public ResponseEntity<HttpResponse> deleteAssessment(@PathVariable("assessmentIdentifier") String assessmentIdentifier) throws NotFoundException {
        assessmentService.deleteAssessment(assessmentIdentifier);
        return response(OK, ASSESSMENT_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
