package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Question;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.QuestionService;
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
@RequestMapping(value = "/api/v1/question")
@RestController
public class QuestionController {
    private static final String QUESTION_DELETED_SUCCESSFULLY = "Question deleted successfully";
    private QuestionService questionService;
    private QuestionService assessmentService;
    private ValidationErrorService validationErrorService;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionService assessmentService, ValidationErrorService validationErrorService) {
        this.questionService = questionService;
        this.assessmentService = assessmentService;
        this.validationErrorService = validationErrorService;
    }

    // create question
    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO assessmentDTO, BindingResult result) throws NotFoundException {
        // validate field error
        ResponseEntity<?> errorMap = validationErrorService.ValidationService(result);
        if (errorMap != null) return response(BAD_REQUEST, errorMap.getBody().toString());

        QuestionDTO newQuestion = assessmentService.addQuestion(assessmentDTO);
        return new ResponseEntity<>(newQuestion, OK);
    }

    // get all question
    @GetMapping
    public ResponseEntity<Page<QuestionDTO>> getAllQuestion(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Page<QuestionDTO> questions = questionService.getQuestions(keyword, page, size);
        return new ResponseEntity<>(questions, OK);
    }

    // get single question by question identifier
    @GetMapping("/{questionIdentifier}")
    public ResponseEntity<Question> getQuestion(@PathVariable("questionIdentifier") String questionIdentifier) throws NotFoundException {
        Question question = questionService.getQuestion(questionIdentifier);
        return new ResponseEntity<>(question, OK);
    }

    // update question by question identifier
    @PutMapping
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody QuestionDTO questionDTO) throws NotFoundException {
        QuestionDTO updateQuestion = questionService.updateQuestion(questionDTO);
        return new ResponseEntity<>(updateQuestion, OK);
    }

    // delete question by question identifier
    @DeleteMapping("/{questionIdentifier}")
    public ResponseEntity<HttpResponse> deleteQuestion(@PathVariable("questionIdentifier") String questionIdentifier) throws NotFoundException {
        questionService.deleteQuestion(questionIdentifier);
        return response(OK, QUESTION_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
