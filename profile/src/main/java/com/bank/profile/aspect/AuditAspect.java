package com.bank.profile.aspect;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;

@Aspect
@Component
public class AuditAspect {

    AuditRepository repository;
    AuditMapper mapper;
    ObjectMapper objectMapper;

    @Autowired
    public AuditAspect(AuditRepository repository, AuditMapper mapper, ObjectMapper objectMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }


    @AfterReturning(pointcut = "@annotation(auditSave)", returning = "result")
    public void saveAspect(AuditSave auditSave, Object result) {
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
    @AfterReturning(pointcut = "@annotation(auditUpdate)",returning = "result")
    public void updateAspect(AuditUpdate auditUpdate, Object result) {
        try {
            if (result != null) {
                // Используем геттер для получения id
                Method getIdMethod = result.getClass().getMethod("getId");
                Long id = (Long) getIdMethod.invoke(result);
                Audit create = repository.findCreateAuditRecordByEntityAndId(auditUpdate.entityType(), id).get();
                Audit update = Audit.builder()
                        .entityType(auditUpdate.entityType())
                        .operationType("update")
                        .createdBy(create.getCreatedBy())
                        .createdAt(create.getCreatedAt())
                        .modifiedAt(OffsetDateTime.now())
                        .modifiedBy("user")
                        .newEntityJson(serializeEntity(result))
                        .entityJson(create.getEntityJson())
                        .build();
                repository.save(update);

            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }



    private String serializeEntity(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing entity", e);
        }
    }


}
