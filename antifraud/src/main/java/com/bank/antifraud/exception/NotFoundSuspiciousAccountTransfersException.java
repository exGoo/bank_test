package com.bank.antifraud.exception;

import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

@Slf4j
public class NotFoundSuspiciousAccountTransfersException extends NotFoundException {

    public NotFoundSuspiciousAccountTransfersException(Long id) {
        super("Suspicious account transfers with id " + id + " not found");
        log.error("Suspicious account transfers with id {} not found", id);
    }
}
