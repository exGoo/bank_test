package com.bank.history.service.impl;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryCreationException;
import com.bank.history.exception.HistoryDeletionException;
import com.bank.history.exception.HistoryListNotFoundException;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.exception.HistoryUpdateException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.model.History;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация интерфейса {@link HistoryService} для управления сущностями истории.
 * Предоставляет методы для создания, обновления, получения и удаления историй.
 * <p>
 * Аннотации:
 * <ul>
 *   <li>{@link RequiredArgsConstructor} — автоматически генерирует конструктор для final-полей.</li>
 *   <li>{@link Service} — указывает, что этот класс является компонентом уровня сервиса.</li>
 *   <li>{@link Transactional} — управляет транзакциями при работе с базой данных.</li>
 * </ul>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {
    /**
     * Репозиторий для работы с сущностями {@link History}.
     */
    private final HistoryRepository repository;
    /**
     * Маппер для преобразования между сущностями {@link History} и DTO {@link HistoryDto}.
     */
    private final HistoryMapper mapper;

    /**
     * Возвращает список всех историй в виде DTO.
     *
     * @return список DTO всех историй.
     * @throws HistoryListNotFoundException если не удалось получить список историй.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getAllHistories() {
        try {
            return mapper.historiesToDtoList(repository.findAll());
        } catch (Exception exception) {
            log.error("Не удалось получить все истории", exception);
            throw new HistoryListNotFoundException();
        }
    }

    /**
     * Возвращает историю по указанному идентификатору в виде DTO.
     *
     * @param id идентификатор истории.
     * @return DTO найденной истории.
     * @throws HistoryNotFoundException если история с указанным идентификатором не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public HistoryDto getHistoryById(Long id) {
        try {
            return mapper.historyToDto(repository.findById(id)
                    .orElseThrow(HistoryNotFoundException::new));
        } catch (Exception exception) {
            log.error("История не найдена по id: {}", id, exception);
            throw new HistoryNotFoundException();
        }
    }

    /**
     * Создает новую историю на основе переданного DTO.
     *
     * @param historyDto DTO для создания истории.
     * @return созданная сущность {@link History}.
     * @throws HistoryCreationException если не удалось создать историю.
     * @throws NullPointerException     если переданный DTO равен null.
     */
    @Override
    @Transactional
    public History createHistory(HistoryDto historyDto) {
        if (historyDto == null) {
            throw new IllegalArgumentException("HistoryDto не должно быть null");
        }
        try {
            return repository.save(mapper.dtoToHistory(historyDto));
        } catch (Exception exception) {
            log.error("Не удалось создать историю: {}", historyDto, exception);
            throw new HistoryCreationException();
        }
    }

    /**
     * Обновляет существующую историю по указанному идентификатору.
     *
     * @param id         идентификатор обновляемой истории.
     * @param historyDto DTO с новыми данными для обновления.
     * @return обновленная сущность {@link History}.
     * @throws HistoryNotFoundException если история с указанным идентификатором не найдена.
     * @throws HistoryUpdateException   если не удалось обновить историю.
     */
    @Override
    @Transactional
    public History updateHistory(Long id, HistoryDto historyDto) {
        try {
            History maybeHistory = repository.findById(id)
                    .orElseThrow(HistoryNotFoundException::new);
            mapper.updateEntityFromDto(historyDto, maybeHistory);
            return repository.save(maybeHistory);
        } catch (HistoryNotFoundException exception) {
            log.error("История не найдена по id: {}", id);
            throw exception;
        } catch (Exception exception) {
            log.error("Не удалось обновить историю по id: {}", id, exception);
            throw new HistoryUpdateException();
        }
    }

    /**
     * Частично обновляет существующую историю по указанному идентификатору.
     *
     * @param id         идентификатор обновляемой истории.
     * @param historyDto DTO с новыми данными для обновления.
     * @return обновленная сущность {@link History}.
     * @throws HistoryNotFoundException если история с указанным идентификатором не найдена.
     * @throws HistoryUpdateException   если не удалось частично обновить историю.
     */
    @Override
    public History editHistory(Long id, HistoryDto historyDto) {
        try {
            History maybeHistory = repository.findById(id)
                    .orElseThrow(HistoryNotFoundException::new);
            mapper.editEntityFromDto(historyDto, maybeHistory);
            return repository.save(maybeHistory);
        } catch (HistoryNotFoundException exception) {
            log.error("История не найдена по id: {}", id, exception);
            throw exception;
        } catch (Exception exception) {
            log.error("Не удалось частично обновить историю по id: {}", id, exception);
            throw new HistoryUpdateException();
        }
    }

    /**
     * Удаляет историю по указанному идентификатору.
     *
     * @param id идентификатор удаляемой истории.
     * @throws HistoryNotFoundException если история с указанным идентификатором не найдена.
     * @throws HistoryDeletionException если не удалось удалить историю.
     */
    @Override
    @Transactional
    public void deleteHistory(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                log.error("История не найдена по id: {}", id);
                throw new HistoryNotFoundException();
            }
        } catch (HistoryNotFoundException exception) {
            log.error("История не найдена по id: {}", id, exception);
            throw exception;
        } catch (Exception exception) {
            log.error("Не удалось удалить историю по id: {}", id, exception);
            throw new HistoryDeletionException();
        }
    }
}
