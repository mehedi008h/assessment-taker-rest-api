package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Assessment findAssessmentByAssessmentIdentifier(String assessmentIdentifier);
}
