package com.bank.history.service.impl;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.model.History;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
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
     */
    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getAllHistories() {
        return mapper.historiesToDtoList(repository.findAll());
    }

    /**
     * Возвращает историю по идентификатору в виде DTO.
     *
     * @param id идентификатор истории.
     * @return DTO найденной истории.
     * @throws HistoryNotFoundException если история не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public HistoryDto getHistoryById(Long id) {
        return mapper.historyToDto(repository.findById(id)
                .orElseThrow(HistoryNotFoundException::new));
    }

    /**
     * Создает новую историю на основе переданного DTO.
     *
     * @param historyDto DTO для создания истории.
     * @return созданная сущность {@link History}.
     */
    @Override
    @Transactional
    public History createHistory(HistoryDto historyDto) {
        return repository.save(mapper.dtoToHistory(historyDto));
    }

    /**
     * Обновляет существующую историю по идентификатору.
     *
     * @param id         идентификатор обновляемой истории.
     * @param historyDto DTO с новыми данными для обновления.
     * @throws HistoryNotFoundException если история не найдена.
     */
    @Override
    @Transactional
    public History updateHistory(Long id, HistoryDto historyDto) {
        History maybeHistory = repository.findById(id)
                .orElseThrow(HistoryNotFoundException::new);

        maybeHistory = History.builder()
                .id(maybeHistory.getId())
                .transferAuditId(historyDto.getTransferAuditId())
                .profileAuditId(historyDto.getProfileAuditId())
                .accountAuditId(historyDto.getAccountAuditId())
                .antiFraudAuditId(historyDto.getAntiFraudAuditId())
                .publicBankInfoAuditId(historyDto.getPublicBankInfoAuditId())
                .authorizationAuditId(historyDto.getAuthorizationAuditId())
                .build();

        return repository.save(maybeHistory);
    }

    /**
     * Частично обновляет существующую историю по идентификатору.
     *
     * @param id         идентификатор обновляемой истории.
     * @param historyDto DTO с новыми данными для обновления.
     * @throws HistoryNotFoundException если история не найдена.
     */
    @Override
    public History editHistory(Long id, HistoryDto historyDto) {
        History maybeHistory = repository.findById(id)
                .orElseThrow(HistoryNotFoundException::new);

        if (historyDto.getTransferAuditId() != null) {
            maybeHistory.setTransferAuditId(historyDto.getTransferAuditId());
        }
        if (historyDto.getProfileAuditId() != null) {
            maybeHistory.setProfileAuditId(historyDto.getProfileAuditId());
        }
        if (historyDto.getAccountAuditId() != null) {
            maybeHistory.setAccountAuditId(historyDto.getAccountAuditId());
        }
        if (historyDto.getAntiFraudAuditId() != null) {
            maybeHistory.setAntiFraudAuditId(historyDto.getAntiFraudAuditId());
        }
        if (historyDto.getPublicBankInfoAuditId() != null) {
            maybeHistory.setPublicBankInfoAuditId(historyDto.getPublicBankInfoAuditId());
        }
        if (historyDto.getAuthorizationAuditId() != null) {
            maybeHistory.setAuthorizationAuditId(historyDto.getAuthorizationAuditId());
        }

        return repository.save(maybeHistory);
    }

    /**
     * Удаляет историю по идентификатору.
     *
     * @param id идентификатор удаляемой истории.
     * @throws HistoryNotFoundException если история не найдена.
     */
    @Override
    @Transactional
    public void deleteHistory(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new HistoryNotFoundException();
        }
    }
}
