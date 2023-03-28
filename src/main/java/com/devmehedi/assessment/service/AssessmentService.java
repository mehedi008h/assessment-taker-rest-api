package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Assessment;

import java.util.Set;

public interface AssessmentService {
    public Assessment addAssessment(Assessment assessment) throws NotFoundException;
    public Assessment updateAssessment(Assessment assessment) throws NotFoundException;
    public Set<Assessment> getAssessments();
    public Assessment getAssessment(String assessmentIdentifier) throws NotFoundException;
    public void deleteAssessment(String assessmentIdentifier) throws NotFoundException;
}
