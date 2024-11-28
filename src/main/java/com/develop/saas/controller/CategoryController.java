package com.develop.saas.controller;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.service.CategoryService;
import jakarta.validation.Valid;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/add")
    public CompletableFuture<ResponseEntity<CategoryResponse>> addCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<ResponseEntity<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}
