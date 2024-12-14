package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.utils.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleEntityNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Test entity not found");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleEntityNotFound(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("Entity not found");
        assertThat(response.getBody().getMessage()).isEqualTo("Test entity not found");
    }

    @Test
    void handleDataValidationError() {
        DataValidationException exception = new DataValidationException("Invalid data provided");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleDataValidationError(exception);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("Incorrect data");
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid data provided");
    }
}
