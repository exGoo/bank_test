package com.bank.publicinfo.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataValidationException extends RuntimeException {

    private String errorMessage;

    public DataValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
