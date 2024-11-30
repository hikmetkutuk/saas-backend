package com.develop.saas.controller;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.service.ScriptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/script")
@Tag(name = "Script", description = "Operations related to script")
public class ScriptController {
    private final ScriptService scriptService;

    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping(value = "/add")
    public CompletableFuture<ResponseEntity<ScriptResponse>> addScript(
            @RequestBody @Valid ScriptRequest scriptRequest) {
        return scriptService.addScript(scriptRequest);
    }
}
