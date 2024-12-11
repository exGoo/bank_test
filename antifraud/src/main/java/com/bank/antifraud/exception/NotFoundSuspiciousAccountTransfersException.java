package com.bank.antifraud.exception;

import org.webjars.NotFoundException;

public class NotFoundSuspiciousAccountTransfersException extends NotFoundException {

    public NotFoundSuspiciousAccountTransfersException(Long id) {
        super("Suspicious account transfer with id " + id + " was not found");
    }
}
