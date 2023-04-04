package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.EmailExistException;
import com.devmehedi.assessment.exception.model.UserNotFoundException;
import com.devmehedi.assessment.exception.model.UsernameExistException;
import com.devmehedi.assessment.model.User;

public interface UserService {
    User register(String firstName, String lastName, String username, String email,String password) throws UserNotFoundException, EmailExistException, UsernameExistException;
    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
