package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.dto.QuestionDTO;
import com.devmehedi.assessment.dto.ResultDTO;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.mapper.QuestionMapper;
import com.devmehedi.assessment.mapper.ResultMapper;
import com.devmehedi.assessment.model.*;
import com.devmehedi.assessment.repository.*;
import com.devmehedi.assessment.service.QuestionService;
import com.devmehedi.assessment.service.ResultService;
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
public class ResultServiceImpl implements ResultService {
    private AssessmentRepository assessmentRepository;
    private CategoryRepository categoryRepository;
    private AnswerRepository answerRepository;
    private ResultRepository resultRepository;
    private UserRepository userRepository;
    private QuestionService questionService;
    private ResultMapper resultMapper;
    private QuestionMapper questionMapper;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public ResultServiceImpl(AssessmentRepository assessmentRepository, CategoryRepository categoryRepository, AnswerRepository answerRepository, ResultRepository resultRepository, UserRepository userRepository, QuestionService questionService, ResultMapper resultMapper,  QuestionMapper questionMapper) {
        this.assessmentRepository = assessmentRepository;
        this.categoryRepository = categoryRepository;
        this.answerRepository = answerRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.questionService = questionService;
        this.resultMapper = resultMapper;
        this.questionMapper = questionMapper;
    }

    // eval assessment
    @Override
    public Result evalAssessment(List<QuestionDTO> questionDTOS, String assessmentIdentifier, String username) throws NotFoundException {
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
        if (((marksGot * 100) / totalMark) > 90) {
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

    // find result of an assessment
    public Result findAssessmentResult(String username, String assessmentIdentifier) throws NotFoundException {
        // find user
        User user = findUserByUsername(username);
        // find assessment
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        // find result
        Result result = resultRepository.findResultByUserAndAssessment(user, assessment);
        if (result == null) {
            throw new NotFoundException("User not taken this assessment");
        }
        return result;
    }

    // get single assessment leaderboard
    @Override
    public Page<ResultDTO> getLeaderBoardsOfAnAssessment(String assessmentIdentifier, int page, int size) throws NotFoundException {
        PageRequest pageRequest = PageRequest.of(page, size);
        Assessment assessment = checkAssessmentExist(assessmentIdentifier);
        Page<Result> resultsPage = resultRepository.findResultByAssessmentOrderByMarkGot(assessment, pageRequest);
        return new PageImpl<>(resultsPage.getContent()
                .stream()
                .map(result -> resultMapper.fromResult(result))
                .collect(Collectors.toList()), pageRequest, resultsPage.getTotalElements());
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

    // find user by username
    public User findUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found with this username " + username);
        }
        return user;
    }
}
