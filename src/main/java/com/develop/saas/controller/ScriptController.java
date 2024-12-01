package com.develop.saas.controller;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.service.ScriptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/script")
@Tag(name = "Script", description = "Operations related to script")
public class ScriptController {
    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompletableFuture<ResponseEntity<ScriptResponse>> addScript(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile image,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("categoryIds") List<Long> categoryIds) {

        ScriptRequest scriptRequest = new ScriptRequest(title, description, content, image, isActive, categoryIds);

        return scriptService.addScript(scriptRequest);
    }
}
