package com.bank.account.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AccountCreationException extends ResponseStatusException {
    public AccountCreationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        log.error("AccountCreationException: Выброшено исключение при создании аккаунта - {}", message);
    }
}
