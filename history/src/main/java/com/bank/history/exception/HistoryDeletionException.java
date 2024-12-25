package com.bank.history.exception;

/**
 * Исключение, которое выбрасывается при ошибках удаления истории.
 */
public class HistoryDeletionException extends RuntimeException {

    public HistoryDeletionException() {
        super("Не удалось удалить историю");
    }
}
