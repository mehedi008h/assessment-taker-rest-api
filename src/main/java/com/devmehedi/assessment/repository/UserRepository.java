package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Page<User> findUserByFirstNameContains(String keyword, Pageable pageable);
}
