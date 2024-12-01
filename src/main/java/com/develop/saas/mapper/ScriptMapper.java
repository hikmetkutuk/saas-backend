package com.develop.saas.mapper;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
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
        return new ScriptResponse(
                script.getId(),
                script.getTitle(),
                script.getDescription(),
                script.getContent(),
                script.getImageUrl(),
                script.isActive(),
                null);
    }
}
