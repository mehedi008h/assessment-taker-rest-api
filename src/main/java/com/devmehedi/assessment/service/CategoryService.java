package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.CategoryNotFoundException;
import com.devmehedi.assessment.model.Category;

import java.util.Set;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category) throws CategoryNotFoundException;
    public Set<Category> getCategories();
    public Category getCategory (Long categoryId);
    public void deleteCategory(Long categoryId);
    public Category findCategoryByIdentifier(String categoryIdentifier);
}
