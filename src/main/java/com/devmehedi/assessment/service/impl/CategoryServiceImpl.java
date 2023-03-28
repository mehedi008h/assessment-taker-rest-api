package com.devmehedi.assessment.service.impl;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Category;
import com.devmehedi.assessment.repository.CategoryRepository;
import com.devmehedi.assessment.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
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
        category.setCategoryIdentifier("C" + generateCategoryId());
        return categoryRepository.save(category);
    }

    // update category
    @Override
    public Category updateCategory(Category category) throws NotFoundException {
        Category updateCategory = checkCategoryExist(category.getCategoryIdentifier());
        updateCategory.setTitle(category.getTitle());
        updateCategory.setDescription(category.getDescription());
        categoryRepository.save(updateCategory);
        return updateCategory;
    }

    // get all category
    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(categoryRepository.findAll());
    }

    // get single category
    @Override
    public Category getCategory(String categoryIdentifier) throws NotFoundException {
        Category category = checkCategoryExist(categoryIdentifier);
        return category;
    }

    // delete category
    @Override
    public void deleteCategory(String categoryIdentifier) throws NotFoundException {
        Category category = checkCategoryExist(categoryIdentifier);
        categoryRepository.deleteById(category.getId());
    }

    // generate random string
    private String generateCategoryId() {
        return RandomStringUtils.randomNumeric(10);
    }

    // check category exist or not
    private Category checkCategoryExist(String categoryIdentifier) throws NotFoundException {
        Category category = categoryRepository.findCategoryByCategoryIdentifier(categoryIdentifier);
        // check & throw exception category not found
        if (category == null) {
            throw new NotFoundException("Category not found with this identifier " + categoryIdentifier);
        }
        return category;
    }
}
