package com.devmehedi.assessment.service;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Question;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface QuestionService {
    public QuestionDTO addQuestion(QuestionDTO questionDTO) throws NotFoundException;
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) throws NotFoundException;
    public Page<QuestionDTO> getQuestions(String keyword, int page, int size);
    public Question getQuestion(String questionIdentifier) throws NotFoundException;
    public void deleteQuestion(String questionIdentifier) throws NotFoundException;
}
