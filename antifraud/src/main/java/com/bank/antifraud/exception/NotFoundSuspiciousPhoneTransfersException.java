package com.bank.antifraud.exception;

import org.webjars.NotFoundException;

public class NotFoundSuspiciousPhoneTransfersException extends NotFoundException {

    public NotFoundSuspiciousPhoneTransfersException(Long id) {
        super("Suspicious phone transfer with id" + id + " not found");
    }
}
