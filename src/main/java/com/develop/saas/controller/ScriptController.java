package com.develop.saas.controller;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.service.ScriptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @ModelAttribute ScriptRequest scriptRequest) {

        return scriptService.addScript(scriptRequest);
    }

    @GetMapping(value = "/{id}")
    public CompletableFuture<ResponseEntity<ScriptResponse>> getScriptById(@PathVariable Long id) {
        return scriptService.getScriptById(id);
    }

    @GetMapping(value = "/list")
    public CompletableFuture<ResponseEntity<List<ScriptResponse>>> getAllScripts() {
        return scriptService.getAllScripts();
    }
}
