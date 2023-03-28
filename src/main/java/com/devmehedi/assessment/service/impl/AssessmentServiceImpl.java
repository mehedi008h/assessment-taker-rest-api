package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.Category;
import com.devmehedi.assessment.repository.AssessmentRepository;
import com.devmehedi.assessment.repository.CategoryRepository;
import com.devmehedi.assessment.service.AssessmentService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {
    private AssessmentRepository assessmentRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public AssessmentServiceImpl(AssessmentRepository assessmentRepository, CategoryRepository categoryRepository) {
        this.assessmentRepository = assessmentRepository;
        this.categoryRepository = categoryRepository;
    }

    // create assessment
    @Override
    public Assessment addAssessment(Assessment assessment) throws NotFoundException {
        // set assessment identifier
        assessment.setAssessmentIdentifier("A" + generateAssessmentId());
        Category category = checkCategoryExist(assessment.getCategory().getCategoryIdentifier());
        assessment.setCategory(category);
        return assessmentRepository.save(assessment);
    }

    // update assessment
    @Override
    public Assessment updateAssessment(Assessment assessment) throws NotFoundException {
        Assessment updateAssessment = checkAssessmentExist(assessment.getAssessmentIdentifier());
        updateAssessment.setTitle(assessment.getTitle());
        updateAssessment.setDescription(assessment.getDescription());
        updateAssessment.setAttempt(assessment.getAttempt());
        updateAssessment.setActive(assessment.isActive());
        Category category = checkCategoryExist(assessment.getCategory().getCategoryIdentifier());
        assessment.setCategory(category);
        return assessmentRepository.save(updateAssessment);
    }

    // get assessments
    @Override
    public Set<Assessment> getAssessments() {
        return new LinkedHashSet<>(assessmentRepository.findAll());
    }

    // get single assessment
    @Override
    public Assessment getAssessment(String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        return assessment;
    }

    // delete assessment
    @Override
    public void deleteAssessment(String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        assessmentRepository.deleteById(assessment.getId());
    }

    // generate random string
    private String generateAssessmentId() {
        return RandomStringUtils.randomNumeric(10);
    }

    // check assessment exist or not
    private Assessment checkAssessmentExist(String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = assessmentRepository.findAssessmentByAssessmentIdentifier(assessmentIdentifier);
        // check & throw exception category not found
        if (assessment == null) {
            throw new NotFoundException("Assessment not found with this identifier " + assessmentIdentifier);
        }
        return assessment;
    }

    // check category exist or not
    private Category checkCategoryExist(String categoryIdentifier) throws NotFoundException {
        Category category = categoryRepository.findCategoryByCategoryIdentifier(categoryIdentifier);
        // check & throw exception category not found
        if (category == null) {
            throw new NotFoundException("Category not found with this identifier " + categoryIdentifier);
        }
        return category;
    }
}
