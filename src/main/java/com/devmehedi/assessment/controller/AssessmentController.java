package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.dto.AssessmentDTO;
import com.devmehedi.assessment.exception.ExceptionHandling;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.AssessmentService;
import com.devmehedi.assessment.service.ValidationErrorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/assessment")
@RestController
public class AssessmentController extends ExceptionHandling {
    private AssessmentService assessmentService;
    private static final String ASSESSMENT_DELETED_SUCCESSFULLY = "Assessment Deleted Successfully";
    private ValidationErrorService validationErrorService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, ValidationErrorService validationErrorService) {
        this.assessmentService = assessmentService;
        this.validationErrorService = validationErrorService;
    }

    // create assessment
    @PostMapping
    public ResponseEntity<?> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDTO, BindingResult result) throws NotFoundException {
        // validate field error
        ResponseEntity<?> errorMap = validationErrorService.ValidationService(result);
        if (errorMap != null) return response(BAD_REQUEST, errorMap.getBody().toString());

        AssessmentDTO newAssessment = assessmentService.addAssessment(assessmentDTO);
        return new ResponseEntity<>(newAssessment, OK);
    }

    // get all assessment
    @GetMapping
    public ResponseEntity<Page<AssessmentDTO>> getAllAssessment(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Page<AssessmentDTO> assessments = assessmentService.getAssessments(keyword, page, size);
        return new ResponseEntity<>(assessments, OK);
    }

    // get single assessment by assessment identifier
    @GetMapping("/{assessmentIdentifier}")
    public ResponseEntity<AssessmentDTO> getAssessment(@PathVariable("assessmentIdentifier") String assessmentIdentifier) throws NotFoundException {
        AssessmentDTO assessment = assessmentService.getAssessment(assessmentIdentifier);
        return new ResponseEntity<>(assessment, OK);
    }

    // update assessment by assessment identifier
    @PutMapping
    public ResponseEntity<AssessmentDTO> updateAssessment(@RequestBody AssessmentDTO assessmentDTO) throws NotFoundException {
        AssessmentDTO updateAssessment = assessmentService.updateAssessment(assessmentDTO);
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
