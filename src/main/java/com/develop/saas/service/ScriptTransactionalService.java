package com.develop.saas.service;

import com.develop.saas.dto.ScriptRequest;
import com.develop.saas.dto.ScriptResponse;
import com.develop.saas.exception.PersistenceException;
import com.develop.saas.exception.ProcessingException;
import com.develop.saas.mapper.ScriptMapper;
import com.develop.saas.model.Script;
import com.develop.saas.repository.ScriptRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Single Responsibility Principle (SRP)
 * Transactions are managed on the main thread.
 * The @Async call is prevented from affecting and breaking the transaction context.
 * This is a solution compatible with proxy-based transaction management in Spring.
 */
@Slf4j
@Service
public class ScriptTransactionalService {
    private final ScriptRepository scriptRepository;
    private final FileService fileService;
    private final ScriptMapper scriptMapper;

    public ScriptTransactionalService(
            ScriptRepository scriptRepository, FileService fileService, ScriptMapper scriptMapper) {
        this.scriptRepository = scriptRepository;
        this.fileService = fileService;
        this.scriptMapper = scriptMapper;
    }

    @Transactional
    public ResponseEntity<ScriptResponse> saveScript(ScriptRequest scriptRequest) {
        try {
            Script script = scriptRepository.save(scriptMapper.toScript(scriptRequest));
            fileService.uploadFileToS3(scriptRequest.image());
            log.info("Script saved: {}", scriptRequest.title());
            return ResponseEntity.ok(scriptMapper.fromScript(script));
        } catch (DataIntegrityViolationException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new PersistenceException("Unexpected error processing script: " + scriptRequest.title());
        } catch (RuntimeException e) {
            log.error("Unexpected error processing script: " + scriptRequest.title(), e);
            throw new ProcessingException("Unexpected error processing script: " + scriptRequest.title());
        }
    }
}
