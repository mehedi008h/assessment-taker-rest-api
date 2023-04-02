package com.devmehedi.assessment.service;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Question;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    public QuestionDTO addQuestion(QuestionDTO questionDTO) throws NotFoundException;
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) throws NotFoundException;
    public Page<QuestionDTO> getAssessmentQuestions(String assessmentIdentifier,String keyword, int page, int size) throws NotFoundException;
    public Question getQuestion(String questionIdentifier) throws NotFoundException;
    public void deleteQuestion(String questionIdentifier) throws NotFoundException;
    List listOfAssessmentQuestion(String assessmentIdentifier) throws NotFoundException;
}
