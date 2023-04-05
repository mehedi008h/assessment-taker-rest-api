package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.EmailExistException;
import com.devmehedi.assessment.exception.model.NotAnImageFileException;
import com.devmehedi.assessment.exception.model.UserNotFoundException;
import com.devmehedi.assessment.exception.model.UsernameExistException;
import com.devmehedi.assessment.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User register(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException, EmailExistException, UsernameExistException;
    List<User> getUsers();
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String newPhone, String newDescription, String newWork, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
