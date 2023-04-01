package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Question;

import java.util.Set;

public interface QuestionService {
    public Question addQuestion(Question question);
    public Question updateQuestion(Question question) throws NotFoundException;
    public Set<Question> getQuestions();
    public Question getQuestion(String questionIdentifier) throws NotFoundException;
    public void deleteQuestion(String questionIdentifier) throws NotFoundException;
}
