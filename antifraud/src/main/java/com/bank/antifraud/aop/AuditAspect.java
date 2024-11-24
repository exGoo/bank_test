package com.bank.antifraud.aop;

import com.bank.antifraud.model.Audit;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.util.Admin;
import com.bank.antifraud.util.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import java.time.OffsetDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Transactional
public class AuditAspect {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;
    private final Admin admin;
    private final User user;
    private Audit audit;
    private Audit lastAudit;
    private Object entity;
    private String oldJson;
    private Object result;

    @Pointcut("execution(public * com.bank.antifraud.service.*Service.add(*))")
    public void isServiceAddMethod() {
    }

    @Pointcut("execution(public * com.bank.antifraud.service.*Service.update(*, *))")
    public void isServiceUpdateMethod() {
    }

    @Pointcut("execution(public * com.bank.antifraud.service.*Service.remove(*))")
    public void isServiceRemoveMethod() {
    }

    @After(value = "isServiceAddMethod()" +
            "&& args(obj)",
            argNames = "obj")
    public void afterAdd(Object obj) {
        try {
            audit = Audit.builder()
                    .entityType(obj.getClass().getSimpleName())
                    .operationType("CREATE")
                    .createdBy(admin.getUsername())
                    .createdAt(OffsetDateTime.now())
                    .entityJson(objectMapper.writeValueAsString(obj))
                    .build();
            auditRepository.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around(value = "isServiceUpdateMethod()" +
            "&& args(id, obj)",
            argNames = "pjp, id, obj")
    public Object afterUpdate(ProceedingJoinPoint pjp, Long id, Object obj) {
        try {
            entity = getEntityCondition(pjp, id);
            oldJson = objectMapper.writeValueAsString(entity);
            lastAudit = findLastAudit(oldJson);

            result = pjp.proceed();

            audit = Audit.builder()
                    .entityType(lastAudit.getEntityType())
                    .operationType("UPDATE")
                    .createdBy(lastAudit.getCreatedBy())
                    .createdAt(lastAudit.getCreatedAt())
                    .modifiedBy(user.getUsername())
                    .modifiedAt(OffsetDateTime.now())
                    .newEntityJson(objectMapper.writeValueAsString(entity))
                    .entityJson(oldJson)
                    .build();
            auditRepository.save(audit);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around(value = "isServiceRemoveMethod()" +
            "&& args(id)",
            argNames = "pjp, id")
    public Object afterDelete(ProceedingJoinPoint pjp, Long id) {
        try {
            entity = getEntityCondition(pjp, id);
            oldJson = objectMapper.writeValueAsString(entity);

            result = pjp.proceed();

            lastAudit = findLastAudit(oldJson);
            audit = Audit.builder()
                    .entityType(lastAudit.getEntityType())
                    .operationType("DELETE")
                    .createdBy(lastAudit.getCreatedBy())
                    .createdAt(lastAudit.getCreatedAt())
                    .modifiedBy(admin.getUsername())
                    .modifiedAt(OffsetDateTime.now())
                    .newEntityJson(null)
                    .entityJson(oldJson)
                    .build();
            auditRepository.save(audit);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Object getEntityCondition(JoinPoint joinPoint, Long id) {
        final Class<?> currentService = joinPoint.getTarget().getClass();
        try {
            return currentService.getMethod("get", Long.class)
                    .invoke(joinPoint.getTarget(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Audit findLastAudit(String json) {
        audit = auditRepository.findTopByNewEntityJson(json)
                .orElseGet(() -> auditRepository.findByEntityJson(json)
                        .orElseThrow(() -> new NotFoundException("Last audit was not found")));
        return audit;
    }

}
