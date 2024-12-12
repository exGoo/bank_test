package com.bank.antifraud.exception;

import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

@Slf4j
public class NotFoundSuspiciousCardTransferException extends NotFoundException {

    public NotFoundSuspiciousCardTransferException(Long id) {
        super("Suspicious card transfer with id " + id + "not found");
        log.error("Suspicious card transfer with id {} not found", id);
    }
}
