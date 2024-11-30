package com.develop.saas.service;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.exception.PersistenceException;
import com.develop.saas.exception.ProcessingException;
import com.develop.saas.mapper.ScriptMapper;
import com.develop.saas.model.Script;
import com.develop.saas.repository.ScriptRepository;
import jakarta.transaction.Transactional;
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

    public ScriptService(ScriptRepository scriptRepository, ScriptMapper scriptMapper) {
        this.scriptRepository = scriptRepository;
        this.scriptMapper = scriptMapper;
    }

    @Async
    @Transactional
    public CompletableFuture<ResponseEntity<ScriptResponse>> addScript(ScriptRequest scriptRequest) {
        try {
            log.info("Saving script: {}", scriptRequest.title());
            Script script = scriptRepository.save(scriptMapper.toScript(scriptRequest));
            return CompletableFuture.completedFuture(ResponseEntity.ok(scriptMapper.fromScript(script)));
        } catch (DataIntegrityViolationException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new PersistenceException("Unexpected error processing script: " + scriptRequest.title());
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new ProcessingException("Unexpected error processing script: " + scriptRequest.title());
        }
    }
}
