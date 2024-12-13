package com.bank.publicinfo.aspects;

import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.utils.Auditable;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning(value = "execution(public * com.bank.publicinfo.service.*Service.add*(*))", returning = "entity")
    public void auditSave(Object entity) {
        if (entity instanceof Auditable) {
            Auditable<?> auditableEnity = (Auditable<?>) entity;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

                @Override
                public void afterCommit() {
                    auditService.saveNewAudit(auditableEnity);
                }
            });
        }
    }

    @AfterReturning(value = "execution(public * com.bank.publicinfo.service.*Service.update*(*, *))", returning = "entity")
    public void auditUpdate(Object entity) {
        if (entity instanceof Auditable) {
            Auditable<?> auditableEnity = (Auditable<?>) entity;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

                @Override
                public void afterCommit() {
                    auditService.refreshAudit(auditableEnity);
                }
            });
        }
    }
}
