package com.bank.antifraud.exception;

import org.webjars.NotFoundException;

public class NotFoundSuspiciousCardTransferException extends NotFoundException {
    public NotFoundSuspiciousCardTransferException(Long id) {
        super("Suspicious card transfer with id " + id + "not found");
    }
}
