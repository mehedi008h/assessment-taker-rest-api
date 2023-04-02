package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Set<Question> findByAssessment(Assessment assessment);
    public Question findQuestionByQuestionIdentifier(String questionIdentifier);
    public Page<Question> findQuestionByContentContains(String keyword, Pageable pageable);
    public  Page<Question> findQuestionByAssessmentAndContentContains(Assessment assessment,String keyword, Pageable pageable);
}
