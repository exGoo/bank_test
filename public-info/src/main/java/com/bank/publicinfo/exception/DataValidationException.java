package com.bank.publicinfo.exception;

public class DataValidationException extends RuntimeException {
    private String errorMessage;

    public DataValidationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
