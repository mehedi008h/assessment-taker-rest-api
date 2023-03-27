package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.exception.ExceptionHandling;
import com.devmehedi.assessment.exception.model.CategoryNotFoundException;
import com.devmehedi.assessment.model.Category;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/category")
@RestController
public class CategoryController extends ExceptionHandling {
    private static final String CATEGORY_DELETED_SUCCESSFULLY = "Category Deleted Successfully";
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // create category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(newCategory, OK);
    }

    // get all category
    @GetMapping
    public ResponseEntity<Set<Category>> getAllCategory() {
        Set<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, OK);
    }

    // get single category by category identifier
    @GetMapping("/{categoryIdentifier}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryIdentifier") String categoryIdentifier) throws CategoryNotFoundException {
        Category category = categoryService.getCategory(categoryIdentifier);
        return new ResponseEntity<>(category, OK);
    }

    // update category by category identifier
    @PostMapping("/{categoryIdentifier}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws CategoryNotFoundException {
        Category updateCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(updateCategory, OK);
    }

    // delete category by category identifier
    @DeleteMapping("/{categoryIdentifier}")
    public ResponseEntity<HttpResponse> deleteCategory(@PathVariable("categoryIdentifier") String categoryIdentifier) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryIdentifier);
        return response(OK, CATEGORY_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
