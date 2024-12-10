package com.bank.publicinfo.aspects;

import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.utils.Auditable;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @AfterReturning(value = "execution(public * com.bank.publicinfo.service.*Service.add*(*))", returning = "entity")
    public void auditSave(Object entity) {
        if (entity instanceof Auditable) {
            Auditable<?> auditableEnity = (Auditable<?>) entity;
            auditService.saveNewAudit(auditableEnity);
        }
    }

    @AfterReturning(value = "execution(public * com.bank.publicinfo.service.*Service.update*(*, *))", returning = "entity")
    public void auditUpdate(Object entity) {
        if(entity instanceof Auditable) {
            Auditable<?> auditableEnity = (Auditable<?>) entity;
            auditService.refreshAudit(auditableEnity);
        }
    }
}
