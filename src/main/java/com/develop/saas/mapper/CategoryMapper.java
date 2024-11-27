package com.develop.saas.mapper;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            return null;
        }
        return Category.builder().name(categoryRequest.name()).build();
    }

    public CategoryResponse fromCategory(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryResponse(category.getId(), category.getName());
    }
}
