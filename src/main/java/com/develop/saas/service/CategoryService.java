package com.develop.saas.service;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.exception.CategoryAlreadyExistsException;
import com.develop.saas.exception.CategoryNotFoundException;
import com.develop.saas.exception.CategoryPersistenceException;
import com.develop.saas.exception.CategoryProcessingException;
import com.develop.saas.mapper.CategoryMapper;
import com.develop.saas.model.Category;
import com.develop.saas.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ResponseEntity<CategoryResponse> addCategory(CategoryRequest categoryRequest) {
        try {
            if (categoryRepository.existsByNameIgnoreCase(categoryRequest.name())) {
                throw new CategoryAlreadyExistsException("Category already exists!");
            }

            log.info("Saving category: {}", categoryRequest.name());
            Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));

            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);

            return ResponseEntity.ok(categoryResponse);
        } catch (CategoryAlreadyExistsException e) {
            log.warn("Category already exists: " + categoryRequest.name());
            throw e;
        } catch (DataIntegrityViolationException e) {
            log.error("Error saving category: " + categoryRequest.name(), e);
            throw new CategoryPersistenceException("Error saving category: " + categoryRequest.name());
        } catch (RuntimeException e) {
            log.error("Unexpected error processing category: " + categoryRequest.name(), e);
            throw new CategoryProcessingException("Unexpected error processing category: " + categoryRequest.name());
        }
    }

    public ResponseEntity<CategoryResponse> getCategory(Long id) {
        try {
            Category category = categoryRepository
                    .findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);
            log.info("Getting category with id: {}", id);
            return ResponseEntity.ok(categoryResponse);
        } catch (CategoryNotFoundException e) {
            log.warn("Category not found with id: " + id);
            throw e;
        } catch (Exception e) {
            log.error("Error getting category: " + id, e);
            throw new CategoryProcessingException("Error getting category: " + id);
        }
    }
}
