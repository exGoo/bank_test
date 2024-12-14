package com.bank.antifraud.aop;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.util.users.Admin;
import com.bank.antifraud.util.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import static com.bank.antifraud.annotation.Auditable.Action;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final OffsetDateTime timeStamp = OffsetDateTime.now();
    private final User user;
    private final Admin admin;
    private Audit audit;

    @Around("@annotation(auditable)")
    public Object auditing(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        log.info("Invoke auditing method during execution: {}", joinPoint.getSignature().getName());
        final Action action = auditable.action();
        Object result = null;
        try {
            result = joinPoint.proceed();
            log.info("JoinPoint method was be executed");
            if (action.equals(Action.CREATE)) {
                auditService.save(ifCreate(auditable.entityType().getStringEntityType(), action, result));
                log.info("Audit was created: {}", audit);
            } else if (action.equals(Action.UPDATE)) {
                final Audit oldAudit = auditService.getFirstAudit(auditable.entityType().getStringEntityType(),
                        action.getStringAction(),
                        (Long) joinPoint.getArgs()[0]);
                auditService.save(ifUpdate(oldAudit, result));
                log.info("Audit: {} was updated with new entry: {}", oldAudit, audit);
            }
        } catch (JsonProcessingException e) {
            log.error("Error during serialization of object: {}, error message {}", result, e.getMessage());
            throw new RuntimeException(e);
        } catch (Throwable e) {
            log.error("During execution of method {} an error occurred: {}",
                    joinPoint.getSignature().getName(),
                    e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    private Audit ifCreate(String entityType, Action action, Object result) throws JsonProcessingException {
        return audit = Audit.builder()
                .entityType(entityType)
                .operationType(action.getStringAction())
                .createdBy(admin.getUsername())
                .createdAt(timeStamp)
                .entityJson(objectMapper.writeValueAsString(result))
                .build();
    }

    private Audit ifUpdate(Audit oldAudit, Object result) throws JsonProcessingException {
        return audit = Audit.builder()
                .entityType(oldAudit.getEntityType())
                .operationType(Action.UPDATE.getStringAction())
                .createdBy(oldAudit.getCreatedBy())
                .createdAt(oldAudit.getCreatedAt())
                .modifiedBy(user.getUsername())
                .modifiedAt(timeStamp)
                .newEntityJson(objectMapper.writeValueAsString(result))
                .entityJson(oldAudit.getEntityJson())
                .build();
    }
}
