package com.devmehedi.assessment.repository;

import com.devmehedi.assessment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
