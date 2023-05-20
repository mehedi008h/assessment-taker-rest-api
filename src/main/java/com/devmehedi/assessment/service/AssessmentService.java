package com.devmehedi.assessment.service;

import com.devmehedi.assessment.dto.AssessmentDTO;
import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.Question;
import com.devmehedi.assessment.model.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssessmentService {
    public AssessmentDTO addAssessment(AssessmentDTO assessmentDTO) throws NotFoundException;
    public AssessmentDTO updateAssessment(AssessmentDTO assessmentDTO) throws NotFoundException;
    public Page<AssessmentDTO> getAssessments(String keyword, int page, int size);
    public AssessmentDTO getAssessment(String assessmentIdentifier) throws NotFoundException;
    public void deleteAssessment(String assessmentIdentifier) throws NotFoundException;
}
