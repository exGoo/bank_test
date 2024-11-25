package com.bank.history.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Класс исключения, представляющий ситуацию, когда искомая история не найдена.
 * <p>
 * Это исключение наследуется от {@link ResponseStatusException} и возвращает HTTP-статус 404 (Not Found).
 * <p>
 */

public class HistoryNotFoundException extends ResponseStatusException {
    /**
     * Конструктор, создающий исключение с HTTP-статусом 404 и сообщением "История не найдена".
     */
    public HistoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "История не найдена");
    }
}
