package com.bank.profile.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class ExceptionHandlerTest {



    @InjectMocks
    ExceptionHandler exceptionHandler;


    @Test
    void handleEntityNotFoundException() {
    EntityNotFoundException exception = new EntityNotFoundException("Passport not found with ID: 1");
      ResponseEntity<Map<String,Object>> result = exceptionHandler.handleEntityNotFoundException(exception);

      assertEquals(HttpStatus.NOT_FOUND.value(), result.getBody().get("status"));
      assertEquals("Passport not found with ID: 1", result.getBody().get("message"));
    }

    @Test
    void handleValidationExceptions() {
        // Подготовка: создание исключения с ошибками валидации
        BindException bindException = new BindException(new Object(), "test");
        bindException.addError(new FieldError("test", "fieldName", "Поле не должно быть пустым"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindException);

        // Вызов обработчика
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(exception);

        // Проверки
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Поле не должно быть пустым", response.getBody().get("fieldName"));
    }
}