package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.mapper.QuestionMapper;
import com.devmehedi.assessment.model.Assessment;
import com.devmehedi.assessment.model.Question;
import com.devmehedi.assessment.repository.AssessmentRepository;
import com.devmehedi.assessment.repository.QuestionRepository;
import com.devmehedi.assessment.service.QuestionService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;
    private AssessmentRepository assessmentRepository;
    private QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper, AssessmentRepository assessmentRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDTO) throws NotFoundException {
        // copy from questionDTO
        Question question = questionMapper.fromQuestionDTO(questionDTO);
        // check assessment exist
        Assessment assessment = checkAssessmentExist(questionDTO.getAssessment().getAssessmentIdentifier());
        // set question identifier
        question.setQuestionIdentifier("Q" + generateQuestionId());
        question.setAssessment(assessment);
        Question newQuestion = questionRepository.save(question);
        return questionMapper.fromQuestion(newQuestion);
    }

    @Override
    public QuestionDTO updateQuestion(QuestionDTO questionDTO) throws NotFoundException {
        Question loadQuestion = checkQuestionExist(questionDTO.getQuestionIdentifier());
        // check assessment exist
        Assessment assessment = checkAssessmentExist(questionDTO.getAssessment().getAssessmentIdentifier());
        // copy from questionDTO
        Question question = questionMapper.fromQuestionDTO(questionDTO);
        question.setAssessment(assessment);
        Question updateQuestion = questionRepository.save(question);
        return questionMapper.fromQuestion(updateQuestion);
    }

    @Override
    public Page<QuestionDTO> getQuestions(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Question> questionPage = questionRepository.findQuestionByContentContains(keyword, pageRequest);

        return new PageImpl<>(questionPage.getContent()
                .stream()
                .map(question -> questionMapper.fromQuestion(question))
                .collect(Collectors.toList()), pageRequest, questionPage.getTotalElements());
    }

    @Override
    public Question getQuestion(String questionIdentifier) throws NotFoundException {
        Question question = checkQuestionExist(questionIdentifier);
        return question;
    }

    @Override
    public void deleteQuestion(String questionIdentifier) throws NotFoundException {
        Question question = checkQuestionExist(questionIdentifier);
        questionRepository.deleteById(question.getId());
    }

    // generate random string
    private String generateQuestionId() {
        return RandomStringUtils.randomNumeric(10);
    }

    // check question exist or not
    private Question checkQuestionExist(String questionIdentifier) throws NotFoundException {
        Question question = questionRepository.findQuestionByQuestionIdentifier(questionIdentifier);
        // check & throw exception question not found
        if (question == null) {
            throw new NotFoundException("Question not found with this identifier " + questionIdentifier);
        }
        return question;
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
}
