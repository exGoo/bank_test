package com.bank.antifraud.exception;

import org.webjars.NotFoundException;

public class NotFoundAuditException extends NotFoundException {
    public NotFoundAuditException(Long id) {
        super("Audit with id" + id + " not found.");
    }
}
