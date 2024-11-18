package com.bank.antifraud.exception;

import org.webjars.NotFoundException;

public class NotFounSuspiciousCardTransferException extends NotFoundException {
    public NotFounSuspiciousCardTransferException(Long id) {
        super("Suspicious card transfer with id " + id + "not found");
    }
}
