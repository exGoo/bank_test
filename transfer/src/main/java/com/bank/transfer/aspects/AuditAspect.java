package com.bank.transfer.aspects;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.Audit;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.bank.transfer.aspects.OperationTypes.CREATE;

@Component
@Aspect
public class AuditAspect {
    private final AuditRepository auditRepository; // Репозиторий для аудита
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditAspect(AuditRepository auditRepository, ObjectMapper objectMapper) {
        this.auditRepository = auditRepository;
        this.objectMapper = objectMapper;
    }


    @Pointcut("execution(* save*Transfer(..))")
    public void createMethod() {
    }

    @Pointcut("execution(* update*Transfer(..))")
    public void updateMethod() {
    }


    @AfterReturning(value = "createMethod()", returning = "result")
    public void runSaveMethods(Object result) {
        final Long id = extractId(result);
        System.out.println(result);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Обработка исключения
        }

        final Audit audit = Audit.builder()
                .entityType(result.getClass().getSimpleName())
                .operationType(OperationTypes.CREATE.name())
                .createdBy(String.valueOf(id))
                .createdAt(LocalDateTime.now())
                .entityJson(jsonString)
                .build();

        auditRepository.save(audit);
        System.out.println("Audit saved: " + audit);
    }

    @AfterReturning(value = "updateMethod()", returning = "result")
    public void runUpdateMethods(Object result) {
        final String entityType = result.getClass().getSimpleName();
        System.out.println(result);
        System.out.println(entityType);
        System.out.println(result);
        String jsonString = null;
        final Long id = extractId(result);
        try {
            jsonString = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Обработка исключения
        }
        final String createByString = String.valueOf(id);
        final Audit audit = auditRepository.findByCreatedByAndOperationType(createByString, CREATE.name());

        final Audit updateAudit = Audit.builder()
                .entityType(entityType)
                .operationType(OperationTypes.UPDATE.name())
                .createdBy(audit.getCreatedBy())
                .modifiedBy(String.valueOf(id))
                .createdAt(audit.getCreatedAt())
                .modifiedAt(LocalDateTime.now())
                .newEntityJson(jsonString)
                .entityJson(audit.getEntityJson())
                .build();

        auditRepository.save(updateAudit);
        System.out.println(updateAudit);
    }

    private Long extractId(Object result) {
        if (result instanceof AccountTransfer) {
            return ((AccountTransfer) result).getId();
        } else if (result instanceof PhoneTransfer) {
            return ((PhoneTransfer) result).getId();
        } else if (result instanceof CardTransfer) {
            return ((CardTransfer) result).getId();
        }
        return null;
    }
}
