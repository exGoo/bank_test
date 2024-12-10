package com.bank.account.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.BindException;

@Slf4j
@Getter
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATIONERROR = "Получены некорректные данные.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(VALIDATIONERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException e) {
        return new ResponseEntity<>(VALIDATIONERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(AccountNotFoundException e) {
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<String> handleAccountCreationException(AccountCreationException e) {
        return new ResponseEntity<>(e.getReason(), HttpStatus.BAD_REQUEST);
    }
}