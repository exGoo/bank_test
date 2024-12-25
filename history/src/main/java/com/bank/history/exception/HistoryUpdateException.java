package com.bank.history.exception;

/**
 * Исключение, которое выбрасывается при ошибках обновления истории.
 */
public class HistoryUpdateException extends RuntimeException {

    public HistoryUpdateException() {
        super("Не удалось обновить историю");
    }
}
