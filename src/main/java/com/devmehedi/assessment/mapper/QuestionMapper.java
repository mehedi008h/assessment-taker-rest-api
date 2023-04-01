package com.devmehedi.assessment.mapper;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper {
    private AssessmentMapper assessmentMapper;

    @Autowired
    public QuestionMapper(AssessmentMapper assessmentMapper) {
        this.assessmentMapper = assessmentMapper;
    }

    public QuestionDTO fromQuestion(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setAssessment(assessmentMapper.fromAssessment(question.getAssessment()));
        return questionDTO;
    }

    public Question fromQuestionDTO(QuestionDTO questionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(questionDTO, question);
        return question;
    }
}
