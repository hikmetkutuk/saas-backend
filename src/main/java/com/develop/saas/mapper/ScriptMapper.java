package com.develop.saas.mapper;

import com.develop.saas.dto.CategoryResponse;
import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.dto.ScriptUpdateRequest;
import com.develop.saas.model.Category;
import com.develop.saas.model.Script;
import com.develop.saas.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScriptMapper {
    private final CategoryRepository categoryRepository;

    public ScriptMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Script toScript(ScriptRequest scriptRequest) {
        if (scriptRequest == null) {
            return null;
        }
        List<Category> categories = categoryRepository.findAllById(scriptRequest.categoryIds());

        return Script.builder()
                .title(scriptRequest.title())
                .description(scriptRequest.description())
                .content(scriptRequest.content())
                .imageUrl(scriptRequest.image().getOriginalFilename())
                .isActive(scriptRequest.isActive())
                .categories(categories)
                .build();
    }

    public ScriptResponse fromScript(Script script) {
        if (script == null) {
            return null;
        }

        List<CategoryResponse> categories = script.getCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();

        return new ScriptResponse(
                script.getId(),
                script.getTitle(),
                script.getDescription(),
                script.getContent(),
                script.getImageUrl(),
                script.isActive(),
                categories);
    }

    public void updateScriptFromRequest(ScriptUpdateRequest scriptUpdateRequest, Script script) {
        if (scriptUpdateRequest == null || script == null) {
            return;
        }

        if (scriptUpdateRequest.title() != null) {
            script.setTitle(scriptUpdateRequest.title());
        }
        if (scriptUpdateRequest.description() != null) {
            script.setDescription(scriptUpdateRequest.description());
        }
        if (scriptUpdateRequest.content() != null) {
            script.setContent(scriptUpdateRequest.content());
        }
        if (scriptUpdateRequest.image() != null) {
            script.setImageUrl(scriptUpdateRequest.image().getOriginalFilename());
        }
        if (scriptUpdateRequest.isActive() != null) {
            script.setActive(scriptUpdateRequest.isActive());
        }
        if (scriptUpdateRequest.categoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(scriptUpdateRequest.categoryIds());
            script.setCategories(categories);
        }
    }
}
