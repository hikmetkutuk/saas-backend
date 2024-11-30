package com.develop.saas.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ScriptRequest(
        String title,
        String description,
        String content,
        MultipartFile imageUrl,
        boolean isActive,
        List<Long> categoryIds) {}
