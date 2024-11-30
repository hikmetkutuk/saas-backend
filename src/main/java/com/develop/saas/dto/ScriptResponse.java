package com.develop.saas.dto;

import java.util.List;

public record ScriptResponse(
        Long id,
        String title,
        String description,
        String content,
        String imageUrl,
        boolean isActive,
        List<CategoryResponse> categories) {}
