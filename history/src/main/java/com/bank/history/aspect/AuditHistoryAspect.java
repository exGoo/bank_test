package com.bank.history.aspect;

import com.bank.history.dto.AuditDto;
import com.bank.history.model.History;
import com.bank.history.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class AuditHistoryAspect {
    private final AuditService auditService;

    @Pointcut("execution(* com.bank.history.service.HistoryService.createHistory(..))")
    public void createMethod() {
    }

    @Pointcut("execution(* com.bank.history.service.HistoryService.updateHistory(..))")
    public void updateMethod() {
    }

    @Pointcut("execution(* com.bank.history.service.HistoryService.editHistory(..))")
    public void editMethod() {
    }

    @AfterReturning(value = "createMethod()", returning = "result")
    public void createMethodAudit(Object result) {
        if (result instanceof History history) {
            LocalDateTime createdAt = LocalDateTime.now();
            String entityJson = getJson(history);

            AuditDto auditDto = AuditDto.builder()
                    .entityType("history")
                    .operationType("create")
                    .createdBy(history.getId().toString())
                    .createdAt(createdAt)
                    .entityJson(entityJson)
                    .build();

            auditService.createAudit(auditDto);
        }
    }

    @AfterReturning(value = "updateMethod()", returning = "result")
    public void updateMethodAudit(Object result) {
        if (result instanceof History history) {
            AuditDto currentHistory = getCurrentHistory(history);

            AuditDto auditDto = AuditDto.builder()
                    .entityType("history")
                    .operationType("update")
                    .createdBy(history.getId().toString())
                    .modifiedBy(history.getId().toString())
                    .createdAt(currentHistory.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
                    .newEntityJson(getJson(history))
                    .entityJson(currentHistory.getEntityJson())
                    .build();

            auditService.createAudit(auditDto);
        }
    }

    @AfterReturning(value = "editMethod()", returning = "result")
    public void editMethodAudit(Object result) {
        if (result instanceof History history) {
            AuditDto currentHistory = getCurrentHistory(history);

            AuditDto auditDto = AuditDto.builder()
                    .entityType("history")
                    .operationType("edit")
                    .createdBy(history.getId().toString())
                    .modifiedBy(history.getId().toString())
                    .createdAt(currentHistory.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
                    .newEntityJson(getJson(history))
                    .entityJson(currentHistory.getEntityJson())
                    .build();

            auditService.createAudit(auditDto);
        }
    }

    private AuditDto getCurrentHistory(History history) {
        return auditService.getByCreatedBy(history.getId().toString());
    }

    private String getJson(History history) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsString(history);
        } catch (JsonProcessingException jpe) {
            log.error("Поймано исключение при обработке содержимого JSON", jpe);
            throw new RuntimeException(jpe);
        }
    }
}
