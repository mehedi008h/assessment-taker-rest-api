package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.ExceptionHandling;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Result;
import com.devmehedi.assessment.service.AssessmentService;
import com.devmehedi.assessment.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/result")
@RestController
public class ResultController extends ExceptionHandling {
    private AssessmentService assessmentService;
    private ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("/eval-assessment/{assessmentIdentifier}")
    public ResponseEntity<Result> evalAssessment(@RequestBody List<QuestionDTO> questionsDTOS, @PathVariable String assessmentIdentifier, Principal principal) throws NotFoundException {
        Result result = resultService.evalAssessment(questionsDTOS,assessmentIdentifier, principal.getName());
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/{assessmentIdentifier}")
    public ResponseEntity<Result> assessmentResult(@PathVariable String assessmentIdentifier, Principal principal) throws NotFoundException {
        Result result = resultService.findAssessmentResult(assessmentIdentifier, principal.getName());
        return new ResponseEntity<>(result, OK);
    }
}
