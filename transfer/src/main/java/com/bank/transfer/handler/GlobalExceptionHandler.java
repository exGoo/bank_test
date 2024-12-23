package com.bank.transfer.handler;

import com.bank.transfer.exception.EntityIncorrectData;
import com.bank.transfer.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handlerExceptoin (NoSuchEntityException exception) {
        final EntityIncorrectData entityIncorrectData = new EntityIncorrectData();
        entityIncorrectData.setErrorInfo(exception.getMessage());
        return new ResponseEntity<>(entityIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<EntityIncorrectData> handlerExceptoin (Exception exception) {
        final EntityIncorrectData entityIncorrectData = new EntityIncorrectData();
        entityIncorrectData.setErrorInfo(exception.getMessage());
        return new ResponseEntity<>(entityIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
