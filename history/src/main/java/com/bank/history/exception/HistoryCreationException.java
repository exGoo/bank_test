package com.bank.history.exception;

/**
 * Исключение, которое выбрасывается при ошибках создания истории.
 */
public class HistoryCreationException extends RuntimeException {

    public HistoryCreationException() {
        super("Не удалось создать историю");
    }
}
