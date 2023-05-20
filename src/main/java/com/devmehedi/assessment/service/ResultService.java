package com.devmehedi.assessment.service;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.dto.ResultDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ResultService {
    public Result evalAssessment(List<QuestionDTO> questionDTOS, String assessmentIdentifier, String username) throws NotFoundException;
    public Result findAssessmentResult(String username, String assessmentIdentifier) throws NotFoundException;
    public Page<ResultDTO> getLeaderBoardsOfAnAssessment(String keyword, int page, int size);
}
