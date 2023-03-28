package com.devmehedi.assessment.controller;

import com.devmehedi.assessment.exception.ExceptionHandling;
import com.devmehedi.assessment.exception.model.NotFoundException;
import com.devmehedi.assessment.model.Category;
import com.devmehedi.assessment.model.HttpResponse;
import com.devmehedi.assessment.service.CategoryService;
import com.devmehedi.assessment.service.ValidationErrorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/category")
@RestController
public class CategoryController extends ExceptionHandling {
    private static final String CATEGORY_DELETED_SUCCESSFULLY = "Category Deleted Successfully";
    private CategoryService categoryService;
    private ValidationErrorService validationErrorService;

    @Autowired
    public CategoryController(CategoryService categoryService, ValidationErrorService validationErrorService) {
        this.categoryService = categoryService;
        this.validationErrorService = validationErrorService;
    }

    // create category
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult result) {
        // validate field error
        ResponseEntity<?> errorMap = validationErrorService.ValidationService(result);
        if (errorMap != null) return response(BAD_REQUEST, errorMap.getBody().toString());

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
    public ResponseEntity<Category> getCategory(@PathVariable("categoryIdentifier") String categoryIdentifier) throws NotFoundException {
        Category category = categoryService.getCategory(categoryIdentifier);
        return new ResponseEntity<>(category, OK);
    }

    // update category by category identifier
    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws NotFoundException {
        Category updateCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(updateCategory, OK);
    }

    // delete category by category identifier
    @DeleteMapping("/{categoryIdentifier}")
    public ResponseEntity<HttpResponse> deleteCategory(@PathVariable("categoryIdentifier") String categoryIdentifier) throws NotFoundException {
        categoryService.deleteCategory(categoryIdentifier);
        return response(OK, CATEGORY_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
