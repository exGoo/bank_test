package com.bank.history.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Класс исключения, представляющий ситуацию, когда искомая история не найдена.
 * <p>
 * Это исключение наследуется от {@link ResponseStatusException} и возвращает HTTP-статус 404 (Not Found).
 * <p>
 * Аннотации:
 * <ul>
 *   <li>{@link Slf4j} — позволяет использовать логгер для записи предупреждений (WARN) и другой информации.</li>
 * </ul>
 */
@Slf4j
public class HistoryNotFoundException extends ResponseStatusException {
    /**
     * Конструктор, создающий исключение с HTTP-статусом 404 и сообщением "История не найдена".
     * <p>
     * Также записывает сообщение об ошибке в лог с уровнем WARN.
     */
    public HistoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "История не найдена");
    }
}
