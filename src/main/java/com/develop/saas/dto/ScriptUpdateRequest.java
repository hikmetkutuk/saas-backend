package com.develop.saas.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ScriptUpdateRequest(
        @Size(max = 200, message = "Title must not exceed 200 characters.") String title,
        @Size(max = 5000, message = "Description must not exceed 5000 characters.") String description,
        String content,
        MultipartFile image,
        Boolean isActive,
        List<Long> categoryIds) {}
