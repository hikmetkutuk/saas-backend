package com.develop.saas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ScriptRequest(
        @NotBlank(message = "Title is required.") @Size(max = 200, message = "Title must not exceed 200 characters.")
                String title,
        @NotBlank(message = "Description is required.")
                @Size(max = 5000, message = "Description must not exceed 5000 characters.")
                String description,
        @NotBlank(message = "Content is required.") String content,
        MultipartFile image,
        /*
         * Recommends using Boolean instead of boolean.
         * This is because the boolean primitive type does not accept null,
         * but the Boolean object type does accept null.
         * This is especially important when null checking is required during validation.
         */
        @NotNull(message = "Active status is required") Boolean isActive,
        List<Long> categoryIds) {}
