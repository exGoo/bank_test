package com.bank.history.aspect;

import com.bank.history.dto.AuditDto;
import com.bank.history.exception.AuditHistoryAspectException;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.model.History;
import com.bank.history.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Аспект для аудита операций с историей.
 * <p>
 * Этот аспект отслеживает вызовы методов создания,
 * обновления и редактирования истории.
 * После успешного выполнения этих методов он создает записи аудита,
 * которые сохраняются в базу данных.
 * Записи аудита содержат информацию о типе операции,
 * идентификаторе сущности, времени выполнения и других деталях.
 * <p>
 * Аспект использует аннотации Spring AOP для перехвата вызовов
 * методов сервиса {@link com.bank.history.service.HistoryService}.
 * После каждого успешного вызова метода создания, обновления или редактирования истории,
 * аспект создает запись аудита,
 * которая сохраняется с помощью сервиса {@link com.bank.history.service.AuditService}.
 * <p>
 * Поля {@code createdBy} и {@code modifiedBy} в записях аудита
 * принимают идентификатор сущности истории.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class AuditHistoryAspect {
    public static final String ENTITY_TYPE = "history";
    public static final String OPERATION_TYPE_CREATE = "create";
    public static final String OPERATION_TYPE_UPDATE = "update";
    public static final String OPERATION_TYPE_EDIT = "edit";

    private final AuditService auditService;

    @Pointcut("execution(* com.bank.history.service.HistoryService.createHistory(..))")
    public void createMethod() {
    }

    @Pointcut("execution(* com.bank.history.service.HistoryService.updateHistory(..))")
    public void updateMethod() {
    }

    @Pointcut("execution(* com.bank.history.service.HistoryService.editHistory(..))")
    public void editMethod() {
    }

    @AfterReturning(value = "createMethod()", returning = "result")
    public void createMethodAudit(Object result) {
        try {
            if (result instanceof History history) {
                AuditDto auditDto = buildAuditDto(history, OPERATION_TYPE_CREATE, null);
                auditService.createAudit(auditDto);
            }
        } catch (Exception exception) {
            log.error("AuditHistoryAspect: ошибка при добавлении записи в базу данных", exception);
            throw new HistoryNotFoundException();
        }
    }

    @AfterReturning(value = "updateMethod()", returning = "result")
    public void updateMethodAudit(Object result) {
        try {
            if (result instanceof History history) {
                AuditDto currentHistory = getCurrentHistory(history);
                AuditDto auditDto = buildAuditDto(history, OPERATION_TYPE_UPDATE, currentHistory);
                auditService.createAudit(auditDto);
            }
        } catch (Exception exception) {
            log.error("AuditHistoryAspect: ошибка при добавлении записи в базу данных", exception);
            throw new AuditHistoryAspectException();
        }
    }

    @AfterReturning(value = "editMethod()", returning = "result")
    public void editMethodAudit(Object result) {
        try {
            if (result instanceof History history) {
                AuditDto currentHistory = getCurrentHistory(history);
                AuditDto auditDto = buildAuditDto(history, OPERATION_TYPE_EDIT, currentHistory);
                auditService.createAudit(auditDto);
            }
        } catch (Exception exception) {
            log.error("AuditHistoryAspect: ошибка при добавлении записи в базу данных", exception);
            throw new AuditHistoryAspectException();
        }
    }

    private AuditDto buildAuditDto(History history, String operationType, AuditDto currentHistory) {
        AuditDto.AuditDtoBuilder builder = AuditDto.builder()
                .entityType(ENTITY_TYPE)
                .operationType(operationType)
                .createdBy(history.getId().toString());

        if (currentHistory != null) {
            builder.createdAt(currentHistory.getCreatedAt())
                    .modifiedBy(history.getId().toString())
                    .modifiedAt(LocalDateTime.now())
                    .newEntityJson(getJson(history))
                    .entityJson(currentHistory.getEntityJson());
        } else {
            builder.createdAt(LocalDateTime.now())
                    .entityJson(getJson(history));
        }
        return builder.build();
    }

    private AuditDto getCurrentHistory(History history) {
        AuditDto result = auditService.getByCreatedBy(history.getId().toString());
        if (result == null) {
            throw new HistoryNotFoundException();
        }
        return result;
    }

    private String getJson(History history) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsString(history);
        } catch (JsonProcessingException jpe) {
            log.error("Поймано исключение при обработке содержимого JSON", jpe);
            throw new RuntimeException(jpe);
        }
    }
}

