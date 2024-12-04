package com.develop.saas.service;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.dto.ScriptUpdateRequest;
import com.develop.saas.exception.NotFoundException;
import com.develop.saas.exception.PersistenceException;
import com.develop.saas.exception.ProcessingException;
import com.develop.saas.mapper.ScriptMapper;
import com.develop.saas.model.Script;
import com.develop.saas.repository.ScriptRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScriptService {
    private final ScriptRepository scriptRepository;
    private final ScriptMapper scriptMapper;
    private final FileService fileService;

    public ScriptService(ScriptRepository scriptRepository, ScriptMapper scriptMapper, FileService fileService) {
        this.scriptRepository = scriptRepository;
        this.scriptMapper = scriptMapper;
        this.fileService = fileService;
    }

    @Async
    public CompletableFuture<ResponseEntity<ScriptResponse>> addScript(ScriptRequest scriptRequest) {
        try {
            return CompletableFuture.completedFuture(saveScript(scriptRequest));
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new ProcessingException("Unexpected error processing script: " + scriptRequest.title());
        }
    }

    @Transactional
    public ResponseEntity<ScriptResponse> saveScript(ScriptRequest scriptRequest) {
        try {
            Script script = scriptRepository.save(scriptMapper.toScript(scriptRequest));
            fileService.uploadFileToS3(scriptRequest.image());
            return ResponseEntity.ok(scriptMapper.fromScript(script));
        } catch (DataIntegrityViolationException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new PersistenceException("Unexpected error processing script: " + scriptRequest.title());
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new ProcessingException("Unexpected error processing script: " + scriptRequest.title());
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<ScriptResponse>> getScriptById(Long id) {
        try {
            Script script = scriptRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Script not found: " + id));
            ScriptResponse scriptResponse = scriptMapper.fromScript(script);
            log.info("Script found: {}", scriptResponse.title());
            return CompletableFuture.completedFuture(ResponseEntity.ok(scriptResponse));
        } catch (NotFoundException e) {
            log.warn("Script not found: " + id);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: ", e);
            throw new ProcessingException("Unexpected error processing script: ");
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<List<ScriptResponse>>> getAllScripts() {
        try {
            List<Script> scripts = scriptRepository.findByIsActiveTrueAndDeletedFalse();
            List<ScriptResponse> scriptResponses =
                    scripts.stream().map(scriptMapper::fromScript).toList();
            log.info("Getting all scripts");
            return CompletableFuture.completedFuture(ResponseEntity.ok(scriptResponses));
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: ", e);
            throw new ProcessingException("Unexpected error processing script:");
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<ScriptResponse>> updateScript(
            Long id, ScriptUpdateRequest scriptUpdateRequest) {
        try {
            Script script = scriptRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Script not found: " + id));
            if (scriptUpdateRequest.image() != null) {
                fileService.uploadFileToS3(scriptUpdateRequest.image());
            }
            scriptMapper.updateScriptFromRequest(scriptUpdateRequest, script);
            scriptRepository.save(script);
            log.info("Script updated: {}", scriptUpdateRequest.title());
            return CompletableFuture.completedFuture(ResponseEntity.ok(scriptMapper.fromScript(script)));
        } catch (NotFoundException e) {
            log.warn("Script not found: " + id);
            throw e;
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: ", e);
            throw new ProcessingException("Unexpected error processing script: ");
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> softDeleteScript(Long id) {
        try {
            Script script = scriptRepository
                    .findByIdAndDeletedFalse(id)
                    .orElseThrow(() -> new NotFoundException("Script not found: " + id));
            script.setDeleted(true);
            scriptRepository.save(script);
            log.info("Script deleted: {}", id);
            return CompletableFuture.completedFuture(ResponseEntity.ok("Script deleted: " + id));
        } catch (NotFoundException e) {
            log.warn("Script not found: " + id);
            throw e;
        }
    }
}
