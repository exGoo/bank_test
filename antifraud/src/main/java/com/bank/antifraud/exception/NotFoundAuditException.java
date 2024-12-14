package com.bank.antifraud.exception;

import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

@Slf4j
public class NotFoundAuditException extends NotFoundException {

    public NotFoundAuditException(String entityType, String operationType, Long id) {
        super(String.format("Audit with entity: %s, operationType: %s, id of entity: %s not found",
                entityType, operationType, id));
        log.error("Audit with entity: {}, operationType: {}, id of entity: {} not found",
                entityType, operationType, id);
    }
}
