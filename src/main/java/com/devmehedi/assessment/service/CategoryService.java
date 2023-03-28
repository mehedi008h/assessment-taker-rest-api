package com.devmehedi.assessment.service;

import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Category;

import java.util.Set;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category) throws NotFoundException;
    public Set<Category> getCategories();
    public Category getCategory (String categoryIdentifier) throws NotFoundException;
    public void deleteCategory(String categoryIdentifier) throws NotFoundException;
}
