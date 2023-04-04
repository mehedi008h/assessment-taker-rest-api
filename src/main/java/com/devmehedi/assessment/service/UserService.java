package com.devmehedi.assessment.service;

import com.devmehedi.assessment.model.User;

public interface UserService {
    User register(String firstName, String lastName, String username, String email,String password);
    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
