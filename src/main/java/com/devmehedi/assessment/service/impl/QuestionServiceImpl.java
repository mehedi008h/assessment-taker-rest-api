package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Question;
import com.devmehedi.assessment.repository.QuestionRepository;
import com.devmehedi.assessment.service.QuestionService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question addQuestion(Question question) {
        // set question identifier
        question.setQuestionIdentifier(generateQuestionId());
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) throws NotFoundException {
        Question updateQuestion = checkQuestionExist(question.getQuestionIdentifier());
        updateQuestion.setContent(question.getContent());
        updateQuestion.setOption1(question.getOption1());
        updateQuestion.setOption2(question.getOption2());
        updateQuestion.setOption3(question.getOption3());
        updateQuestion.setOption4(question.getOption4());
        updateQuestion.setOption5(question.getOption5());
        updateQuestion.setAnswer(question.getAnswer());
        return questionRepository.save(updateQuestion);
    }

    @Override
    public Set<Question> getQuestions() {
        return new LinkedHashSet<>(questionRepository.findAll());
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
}
