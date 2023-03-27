package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.exception.model.CategoryNotFoundException;
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

    // update category
    @Override
    public Category updateCategory(Category category) throws CategoryNotFoundException {
        Category updateCategory = checkCategoryExist(category.getCategoryIdentifier());
        updateCategory.setTitle(category.getTitle());
        updateCategory.setDescription(category.getDescription());
        categoryRepository.save(updateCategory);
        return updateCategory;
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

    @Override
    public Category findCategoryByIdentifier(String categoryIdentifier) {
        return categoryRepository.findCategoryByCategoryIdentifier(categoryIdentifier);
    }

    // generate random string
    private String generateCategoryId() {
        return RandomStringUtils.randomNumeric(10);
    }

    // check category exist or not
    private Category checkCategoryExist(String categoryIdentifier) throws CategoryNotFoundException {
        Category updateCategory = findCategoryByIdentifier(categoryIdentifier);
        // check category exist or not
        if (updateCategory == null) {
            throw new CategoryNotFoundException("Category not found with this identifier " + categoryIdentifier);
        }

        return updateCategory;
    }
}
