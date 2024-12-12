package com.bank.antifraud.exception;

import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

@Slf4j
public class NotFoundSuspiciousPhoneTransfersException extends NotFoundException {

    public NotFoundSuspiciousPhoneTransfersException(Long id) {
        super("Suspicious phone transfer with id" + id + " not found");
        log.error("Suspicious phone transfer with id {} not found", id);
    }
}
