package com.bank.history.exception;

/**
 * Исключение, которое выбрасывается при ошибках получения списка историй.
 */
public class HistoryListNotFoundException extends RuntimeException {

    public HistoryListNotFoundException() {
        super("Не удалось получить список всех историй");
    }
}
