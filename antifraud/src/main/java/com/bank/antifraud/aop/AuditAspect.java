package com.bank.antifraud.aop;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.entity.Audit;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.util.users.Admin;
import com.bank.antifraud.util.users.User;
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
    private final User user;
    private final Admin admin;

    @Around("@annotation(auditable)")
    public Object auditing(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        log.info("Invoke auditing method during execution: {}", joinPoint.getSignature().getName());
        Object result = null;
        final Action action = auditable.action();
        final String entityType = auditable.entityType().getStringEntityType();
        final Audit audit;
        final OffsetDateTime timeStamp = OffsetDateTime.now();
        try {
            result = joinPoint.proceed();
            log.info("Method was be executed");
        } catch (Exception e) {
            log.error("During execution of method {} an error occurred: {}",
                    joinPoint.getSignature().getName(),
                    e.getMessage());
        }
        if (action.equals(Action.CREATE)) {
            audit = Audit.builder()
                    .entityType(entityType)
                    .operationType(action.getStringAction())
                    .createdBy(admin.getUsername())
                    .createdAt(timeStamp)
                    .entityJson(objectMapper.writeValueAsString(result))
                    .build();
            auditService.save(audit);
            log.info("Audit was created: {}", audit);
        } else if (action.equals(Action.UPDATE)) {
            audit = auditService.getFirstAudit(entityType,
                    action.getStringAction(),
                    (Long) joinPoint.getArgs()[0]);
            final Audit newAudit = Audit.builder()
                    .entityType(entityType)
                    .operationType(action.getStringAction())
                    .createdBy(audit.getCreatedBy())
                    .createdAt(audit.getCreatedAt())
                    .modifiedBy(user.getUsername())
                    .modifiedAt(timeStamp)
                    .newEntityJson(objectMapper.writeValueAsString(result))
                    .entityJson(audit.getEntityJson())
                    .build();
            auditService.save(newAudit);
            log.info("Audit: {} was updated with new entry: {}", audit, newAudit);
        }
        return result;
    }
}
