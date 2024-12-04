package com.bank.profile.aspect;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.dto.util.Identifiable;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    private static final String OPERATION_CREATE = "create";
    private static final String OPERATION_UPDATE = "update";
    private static final String USER = "user";


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
        log.info("попытка сохранить audit для {}", auditSave.entityType());
        try {
            String entityJson = serializeEntity(result);
            Audit audit = Audit.builder()
                    .entityType(auditSave.entityType())
                    .operationType(OPERATION_CREATE)
                    .createdBy(USER)
                    .createdAt(OffsetDateTime.now())
                    .entityJson(entityJson)
                    .build();
            Audit save = repository.save(audit);
            log.info("audit сохранен с ID:{} ", save.getId());
        } catch (Exception e) {
            log.error("ошибка при сохранении audit: {}", e.getMessage());
            throw e;
        }
    }

    @AfterReturning(pointcut = "@annotation(auditUpdate)", returning = "result")
    public void updateAspect(AuditUpdate auditUpdate, Object result) {
        log.info("попытка сохранить audit для {}", auditUpdate.entityType());
        try {
            if (result != null) {
                Long id = ((Identifiable) result).getId();
                Audit create = repository.findCreateAuditRecordByEntityAndId(auditUpdate.entityType(), id).get();
                Audit update = Audit.builder()
                        .entityType(auditUpdate.entityType())
                        .operationType(OPERATION_UPDATE)
                        .createdBy(create.getCreatedBy())
                        .createdAt(create.getCreatedAt())
                        .modifiedAt(OffsetDateTime.now())
                        .modifiedBy(USER)
                        .newEntityJson(serializeEntity(result))
                        .entityJson(create.getEntityJson())
                        .build();
                Audit save = repository.save(update);
                log.info("audit сохранен с ID:{} ", save.getId());
            }
        } catch (Exception e) {
            log.error("ошибка при сохранении audit: {}", e.getMessage());
            throw e;
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
