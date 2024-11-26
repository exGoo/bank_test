package com.bank.antifraud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Slf4j
@RestController
public class ExceptionsHandlersController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Incorrect body of request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public void handleLogException(Exception e) {
        log.error("cause: {}, message: {}", e.getCause(), e.getMessage());
    }

}
