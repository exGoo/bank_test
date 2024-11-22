package com.bank.profile.aspect;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Aspect
@Component
public class AuditAspect {

    AuditRepository repository;
    AuditMapper mapper;

    @Autowired
    public AuditAspect(AuditRepository repository, AuditMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @AfterReturning(pointcut = "@annotation(auditSave)", returning = "result")
    public void saveAspect(JoinPoint joinPoint, AuditSave auditSave, Object result) {
        String entityJson = serializeEntity(result);
        Audit audit = Audit.builder()
                .entityType(auditSave.entityType())
                .operationType("create")
                .createdBy("user")
                .createdAt(OffsetDateTime.now())
                .entityJson(entityJson)
                .build();
        repository.save(audit);
    }

    private String serializeEntity(Object entity) {
        try {
            return new ObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing entity", e);
        }
    }


}
