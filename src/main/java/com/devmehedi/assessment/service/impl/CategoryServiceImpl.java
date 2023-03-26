package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.model.Category;
import com.devmehedi.assessment.repository.CategoryRepository;
import com.devmehedi.assessment.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // create new category
    @Override
    public Category addCategory(Category category) {
        // set category identifier
        category.setCategoryIdentifier(generateCategoryId());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Set<Category> getCategories() {
        return null;
    }

    @Override
    public Category getCategory(Long categoryId) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }

    // generate random string
    private String generateCategoryId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
