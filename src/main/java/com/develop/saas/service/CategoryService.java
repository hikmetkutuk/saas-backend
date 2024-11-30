package com.develop.saas.service;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.exception.AlreadyExistsException;
import com.develop.saas.exception.NotFoundException;
import com.develop.saas.exception.PersistenceException;
import com.develop.saas.exception.ProcessingException;
import com.develop.saas.mapper.CategoryMapper;
import com.develop.saas.model.Category;
import com.develop.saas.repository.CategoryRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public CompletableFuture<ResponseEntity<CategoryResponse>> addCategory(CategoryRequest categoryRequest) {
        try {
            if (categoryRepository.existsByNameIgnoreCase(categoryRequest.name())) {
                throw new AlreadyExistsException("Category already exists!");
            }

            log.info("Saving category: {}", categoryRequest.name());
            Category category = categoryRepository.save(categoryMapper.toCategory(categoryRequest));

            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);

            return CompletableFuture.completedFuture(ResponseEntity.ok(categoryResponse));
        } catch (AlreadyExistsException e) {
            log.warn("Category already exists: " + categoryRequest.name());
            throw e;
        } catch (DataIntegrityViolationException e) {
            log.error("Error saving category: " + categoryRequest.name(), e);
            throw new PersistenceException("Error saving category: " + categoryRequest.name());
        } catch (RuntimeException e) {
            log.error("Unexpected error processing category: " + categoryRequest.name(), e);
            throw new ProcessingException("Unexpected error processing category: " + categoryRequest.name());
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<CategoryResponse>> getCategory(Long id) {
        try {
            Category category = categoryRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);
            log.info("Getting category with id: {}", id);
            return CompletableFuture.completedFuture(ResponseEntity.ok(categoryResponse));
        } catch (NotFoundException e) {
            log.warn("Category not found with id: " + id);
            throw e;
        } catch (Exception e) {
            log.error("Error getting category: " + id, e);
            throw new ProcessingException("Error getting category: " + id);
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<List<CategoryResponse>>> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findByDeletedFalse();
            List<CategoryResponse> categoryResponses =
                    categories.stream().map(categoryMapper::fromCategory).toList();
            log.info("Getting all categories");
            return CompletableFuture.completedFuture(ResponseEntity.ok(categoryResponses));
        } catch (Exception e) {
            log.error("Error getting all categories", e);
            throw new ProcessingException("Error getting all categories");
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<CategoryResponse>> updateCategory(
            Long id, CategoryRequest categoryRequest) {
        try {
            Category category = categoryRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
            category.setName(categoryRequest.name());
            categoryRepository.save(category);
            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);
            log.info("Updating category with id: {}", id);
            return CompletableFuture.completedFuture(ResponseEntity.ok(categoryResponse));
        } catch (NotFoundException e) {
            log.warn("Category not found with id: " + id);
            throw e;
        } catch (Exception e) {
            log.error("Error updating category: " + id, e);
            throw new ProcessingException("Error updating category: " + id);
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> softDeleteCategory(Long id) {
        try {
            Category category = categoryRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
            category.setDeleted(true);
            categoryRepository.save(category);
            CategoryResponse categoryResponse = categoryMapper.fromCategory(category);
            log.info("Category deleted successfully: {}", categoryResponse.name());
            return CompletableFuture.completedFuture(
                    ResponseEntity.ok("Category deleted successfully: " + categoryResponse.name()));
        } catch (NotFoundException e) {
            log.warn("Category not found with id: " + id);
            throw e;
        } catch (Exception e) {
            log.error("Error soft deleting category: " + id, e);
            throw new ProcessingException("Error soft deleting category: " + id);
        }
    }
}
