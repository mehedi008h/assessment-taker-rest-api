package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.dto.AssessmentDTO;
import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.mapper.AssessmentMapper;
import com.devmehedi.assessment.mapper.QuestionMapper;
import com.devmehedi.assessment.model.*;
import com.devmehedi.assessment.repository.*;
import com.devmehedi.assessment.service.AssessmentService;
import com.devmehedi.assessment.service.QuestionService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssessmentServiceImpl implements AssessmentService {
    private AssessmentRepository assessmentRepository;
    private CategoryRepository categoryRepository;
    private AnswerRepository answerRepository;
    private ResultRepository resultRepository;
    private UserRepository userRepository;
    private QuestionService questionService;
    private AssessmentMapper assessmentMapper;
    private QuestionMapper questionMapper;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public AssessmentServiceImpl(AssessmentRepository assessmentRepository, CategoryRepository categoryRepository, AnswerRepository answerRepository, ResultRepository resultRepository, UserRepository userRepository, QuestionService questionService, AssessmentMapper assessmentMapper, QuestionMapper questionMapper) {
        this.assessmentRepository = assessmentRepository;
        this.categoryRepository = categoryRepository;
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.questionService = questionService;
        this.assessmentMapper = assessmentMapper;
        this.questionMapper = questionMapper;
    }

    // create assessment
    @Override
    public AssessmentDTO addAssessment(AssessmentDTO assessmentDTO) throws NotFoundException {
        // copy assessmentDTO
        Assessment assessment = assessmentMapper.fromAssessmentDTO(assessmentDTO);
        Category category = checkCategoryExist(assessmentDTO.getCategory().getCategoryIdentifier());
        // set assessment identifier
        assessment.setAssessmentIdentifier("A" + generateRandomId());
        assessment.setCategory(category);
        Assessment newAssessment = assessmentRepository.save(assessment);
        return assessmentMapper.fromAssessment(newAssessment);
    }

    // update assessment
    @Override
    public AssessmentDTO updateAssessment(AssessmentDTO assessmentDTO) throws NotFoundException {
        Assessment loadAssessment = checkAssessmentExist(assessmentDTO.getAssessmentIdentifier());
        Category category = checkCategoryExist(assessmentDTO.getCategory().getCategoryIdentifier());
        // copy assessmentDTO
        Assessment assessment = assessmentMapper.fromAssessmentDTO(assessmentDTO);
        assessment.setCategory(category);
        assessment.setQuestions(loadAssessment.getQuestions());
        Assessment updateAssessment = assessmentRepository.save(assessment);
        return assessmentMapper.fromAssessment(updateAssessment);
    }

    // get assessments
    @Override
    public Page<AssessmentDTO> getAssessments(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Assessment> assessmentPage = assessmentRepository.findAssessmentByTitleContains(keyword, pageRequest);
        return new PageImpl<>(assessmentPage.getContent()
                .stream()
                .map(assessment -> assessmentMapper.fromAssessment(assessment))
                .collect(Collectors.toList()), pageRequest, assessmentPage.getTotalElements());
    }

    // get single assessment
    @Override
    public AssessmentDTO getAssessment(String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        return assessmentMapper.fromAssessment(assessment);
    }

    // delete assessment
    @Override
    public void deleteAssessment(String assessmentIdentifier) throws NotFoundException {
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        assessmentRepository.deleteById(assessment.getId());
    }

    // eval assessment
    @Override
    public Result evalAssessment(List<QuestionDTO> questionDTOS,String assessmentIdentifier, String username) throws NotFoundException {
        List<Question> questions = questionDTOS.stream().map(questionDTO -> questionMapper.fromQuestionDTO(questionDTO)).collect(Collectors.toList());
        LOGGER.info("username : " + username);
        LOGGER.info("questions : " + questions);
        int marksGot = 0;
        int correctAnswer = 0;
        int totalMark = 0;
        User user = findUserByUsername(username);
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        Result result = new Result();

        for (Question q : questions) {
            totalMark = totalMark + q.getMark();
            // answer
            Answer answer = new Answer();
            answer.setAnswerIdentifier("A" + generateRandomId());
            answer.setGivenAnswer(q.getGivenAnswer());
            answer.setQuestion(q);
            answer.setResult(result);
            answerRepository.save(answer);
            if (q.getQuestionAnswer().equals(q.getGivenAnswer())) {
                marksGot = marksGot + q.getMark();
                correctAnswer++;
            }
        }
        if(((marksGot * 100) / totalMark) > 90) {
            user.setReward(user.getReward() + 1);
        }
        userRepository.save(user);
        // result
        result.setResultIdentifier("R" + generateRandomId());
        result.setMarkGot(marksGot);
        result.setCorrectAnswer(correctAnswer);
        result.setAttempted(1);
        result.setUser(user);
        result.setAssessment(assessment);
        return resultRepository.save(result);
    }

    // generate random string
    private String generateRandomId() {
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

    // find user by username
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
