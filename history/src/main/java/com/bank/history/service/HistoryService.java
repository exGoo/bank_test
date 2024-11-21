package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервисный интерфейс для управления историей.
 * Предоставляет методы для получения, создания, обновления и удаления истории.
 */
public interface HistoryService {
    /**
     * Получает список всех историй.
     *
     * @return список объектов {@link HistoryDto}, представляющих все истории.
     */
    List<HistoryDto> getAllHistories();

    /**
     * Получает запись истории по её идентификатору.
     *
     * @param id идентификатор истории.
     * @return объект {@link HistoryDto}, представляющий найденную историю.
     * @throws EntityNotFoundException если история с указанным идентификатором не найдена.
     */
    HistoryDto getHistoryById(Long id);

    /**
     * Создаёт новую историю на основе переданных данных.
     *
     * @param historyDto объект {@link HistoryDto}, содержащий данные для создания новой истории.
     * @return объект {@link History}, представляющий созданную историю.
     */
    History createHistory(HistoryDto historyDto);

    /**
     * Обновляет существующую историю.
     *
     * @param id         идентификатор истории, которую необходимо обновить.
     * @param historyDto объект {@link HistoryDto}, содержащий обновлённые данные.
     * @throws EntityNotFoundException если история с указанным идентификатором не найдена.
     */
    History updateHistory(Long id, HistoryDto historyDto);

    /**
     * Частично обновляет существующую историю.
     *
     * @param id         идентификатор истории, которую необходимо обновить.
     * @param historyDto объект {@link HistoryDto}, содержащий обновлённые данные.
     * @throws EntityNotFoundException если история с указанным идентификатором не найдена.
     */
    History editHistory(Long id, HistoryDto historyDto);

    /**
     * Удаляет историю по её идентификатору.
     *
     * @param id идентификатор истории, которую необходимо удалить.
     * @throws EntityNotFoundException если история с указанным идентификатором не найдена.
     */
    void deleteHistory(Long id);
}