package com.bank.publicinfo.aspects;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.utils.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Transactional
public class AuditAspect {

    private final ObjectMapper objectMapper;
    private final AuditRepository auditRepository;
    private final Admin admin;
    private Audit audit;
    private Audit lastAudit;
    private Object entity;
    private String oldJson;
    private Object result;

    @Pointcut("execution(public * com.bank.publicinfo.service.*Service.add*(*))")
    public void isAddMethod() {
    }

    @Pointcut("execution(public * com.bank.publicinfo.service.*Service.update*(*, *))")
    public void isUpdateMethod() {
    }

    @Pointcut("execution(public * com.bank.publicinfo.service.*Service.delete*(*))")
    public void isDeleteMethod() {
    }

    @AfterReturning(value = "isAddMethod()", returning = "result")
    public void afterAdd(Object result) {
        try {
            audit = Audit.builder()
                    .entityType(result.getClass().getSimpleName())
                    .operationType("CREATE")
                    .createdBy(admin.getUsername())
                    .createdAt(LocalDateTime.now())
                    .entityJson(objectMapper.writeValueAsString(result))
                    .build();
            auditRepository.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around(value = "isUpdateMethod()" +
            "&& args(id, obj)",
            argNames = "pjp, id, obj")
    public Object afterUpdate(ProceedingJoinPoint pjp, Long id, Object obj) {
        try {
            entity = getEntityCondition(pjp, id);
            oldJson = objectMapper.writeValueAsString(entity);
            result = pjp.proceed();
            entity = getEntityCondition(pjp, id);
            lastAudit = findLastAudit(id, obj.getClass().getSimpleName());
            audit = Audit.builder()
                    .entityType(lastAudit.getEntityType())
                    .operationType("UPDATE")
                    .createdBy(lastAudit.getCreatedBy())
                    .modifiedBy(admin.getUsername())
                    .createdAt(lastAudit.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
                    .newEntityJson(objectMapper.writeValueAsString(entity))
                    .entityJson(oldJson)
                    .build();
            auditRepository.save(audit);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around(value = "isDeleteMethod()" +
            "&& args(id)",
            argNames = "pjp, id")
    public Object afterDelete(ProceedingJoinPoint pjp, Long id) {
        try {

            entity = getEntityCondition(pjp, id);
            oldJson = objectMapper.writeValueAsString(entity);
            result = pjp.proceed();
            lastAudit = findLastAudit(id, entity.getClass().getSimpleName());
            audit = Audit.builder()
                    .entityType(lastAudit.getEntityType())
                    .operationType("DELETE")
                    .createdBy(lastAudit.getCreatedBy())
                    .modifiedBy(admin.getUsername())
                    .createdAt(lastAudit.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
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
            return currentService.getMethod("findById", Long.class)
                    .invoke(joinPoint.getTarget(), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Audit findLastAudit(Long id, String entityType) {
        return auditRepository.findByEntityJsonId(id.toString(), entityType)
                .orElseThrow(() -> new NotFoundException("Аудит для данного id не найден"));
    }

}

