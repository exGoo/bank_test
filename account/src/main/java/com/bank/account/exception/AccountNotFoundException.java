package com.bank.account.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class AccountNotFoundException extends ResponseStatusException {

    public AccountNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Аккаунт cо следующим ID не найден: " + id);
        log.warn("AccountNotFoundException: Был запрошен несуществующий аккаунт (ID: {})", id);
    }

}

