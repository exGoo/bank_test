package com.bank.account.aop;

import com.bank.account.exception.AuditCreationException;
import com.bank.account.model.Account;
import com.bank.account.model.Audit;
import com.bank.account.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class AuditAspect {
    private static final String WATCHDOG = "Account ";
    private static final String CREATE = "create";
    private static final String UPDATE = "update";
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    private final AuditService auditService;
    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @After("execution(void com.bank.account.service.impl.AccountServiceImpl.save(com.bank.account.model.Account)) && args(account)")
    public void afterSave(Account account) {
        try {
            String jsonString = objectConverterToJson(account);
            Audit audit = auditService.findLastAuditByUser(account.getId().toString());

            if (audit == null) {
                Audit newAudit = new Audit();
                newAudit.setEntityType(WATCHDOG);
                newAudit.setOperationType(CREATE);
                newAudit.setCreatedAt(TIMESTAMP);
                newAudit.setCreatedBy(account.getId().toString());
                newAudit.setModifiedAt(null);
                newAudit.setModifiedBy(null);
                newAudit.setEntityJson(jsonString);
                newAudit.setNewEntityJson(null);
                auditService.save(newAudit);
                log.info("Audit: сохранена запись - {}", newAudit);
            }
        } catch (Exception e) {
            log.error("Audit: Возникло исключение при записи в базу, аккаунта не существует.");
            throw new AuditCreationException("Аккаунта не существует, ошибка записи в базу данных");
        }
    }

    @After("execution(void com.bank.account.service.impl.AccountServiceImpl.update(com.bank.account.model.Account)) && args(account)")
    public void afterUpdate(Account account) {
        String jsonString = objectConverterToJson(account);
        Audit audit = auditService.findLastAuditByUser(account.getId().toString());

        Audit newAudit = new Audit();
        newAudit.setEntityType(WATCHDOG);
        newAudit.setOperationType(UPDATE);
        newAudit.setCreatedAt(audit.getCreatedAt());
        newAudit.setCreatedBy(audit.getCreatedBy());
        newAudit.setModifiedAt(TIMESTAMP);
        newAudit.setModifiedBy(account.getId().toString());
        newAudit.setNewEntityJson(jsonString);
        if (audit.getNewEntityJson() == null) {
            newAudit.setEntityJson(audit.getEntityJson());
        } else {
            newAudit.setEntityJson(audit.getNewEntityJson());
        }
        auditService.save(newAudit);
        log.info("Audit: Произведено обновление аккаунта с ID {} - {}", account.getId(), newAudit);
    }

    public String objectConverterToJson(Account account) {
        String jsonString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(account);
        } catch (Exception e) {
            log.warn("Audit: Ошибка преобразования в объект.");
            e.printStackTrace();
        }
        return jsonString;
    }
}