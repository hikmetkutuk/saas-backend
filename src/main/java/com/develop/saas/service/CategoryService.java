package com.develop.saas.service;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.mapper.CategoryMapper;
import com.develop.saas.model.Category;
import com.develop.saas.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ResponseEntity<CategoryResponse> addCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByNameIgnoreCase(categoryRequest.name())) {
            // TODO: Add exception handling
        }

        Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));

        CategoryResponse categoryResponse = categoryMapper.fromCategory(category);
        return ResponseEntity.ok(categoryResponse);
    }
}
