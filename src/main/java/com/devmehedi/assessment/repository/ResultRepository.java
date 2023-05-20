package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.Result;
import com.devmehedi.assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findResultByUserAndAssessment(User user, Assessment assessment);
}
