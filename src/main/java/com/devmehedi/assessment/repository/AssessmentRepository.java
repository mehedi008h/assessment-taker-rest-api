package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    Assessment findAssessmentByAssessmentIdentifier(String assessmentIdentifier);

    // find assessment for admin
    Page<Assessment> findAssessmentByTitleContains(String keyword, Pageable pageable);

    // find assessment for user
    Page<Assessment> findAssessmentByTitleContainsAndActive(String keyword, Boolean b, Pageable pageable);
}
