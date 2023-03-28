package com.devmehedi.assessment.mapper;

import com.devmehedi.assessment.dto.AssessmentDTO;
import com.devmehedi.assessment.model.Assessment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentMapper {
    private CategoryMapper categoryMapper;

    @Autowired
    public AssessmentMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public AssessmentDTO fromAssessment(Assessment assessment) {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        BeanUtils.copyProperties(assessment, assessmentDTO);
        assessmentDTO.setCategory(categoryMapper.fromCategory(assessment.getCategory()));
        return assessmentDTO;
    }

    public Assessment fromAssessmentDTO(AssessmentDTO assessmentDTO) {
        Assessment assessment = new Assessment();
        BeanUtils.copyProperties(assessmentDTO, assessment);
        return assessment;
    }
}
