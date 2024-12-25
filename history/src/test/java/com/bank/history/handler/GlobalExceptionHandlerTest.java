package com.bank.history.handler;

import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.exception.InternalServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HistoryNotFoundException historyNotFoundException;

    @Mock
    private InternalServerException serverException;

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handlerExceptionTest() {
        when(historyNotFoundException.getMessage())
                .thenReturn("История не найдена");

        ResponseEntity<ErrorMessage> actualResult =
                globalExceptionHandler.handlerException(historyNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actualResult.getHeaders().getContentType());
        assertEquals("История не найдена", Objects.requireNonNull(actualResult.getBody()).getMessage());
    }

    @Test
    void handleGenericExceptionTest() {
        when(serverException.getMessage())
                .thenReturn("Ошибка на сервере");

        ResponseEntity<ErrorMessage> actualResult =
                globalExceptionHandler.handleServerException(serverException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResult.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, actualResult.getHeaders().getContentType());
        assertEquals("Ошибка на сервере", Objects.requireNonNull(actualResult.getBody()).getMessage());
    }
}
