 package com.bank.antifraud.service.implementation;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.annotation.Auditable.Action;
import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import static com.bank.antifraud.annotation.Auditable.EntityType.SUSPICIOUS_ACCOUNT_TRANSFERS;

 /**
 * Реализация сервиса для управления подозрительными банковскими переводами.
 * Этот класс предоставляет методы для добавления, получения, обновления и удаления записей
 * о подозрительных переводах, а также для получения всех записей.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousAccountTransfersServiceImpl implements SuspiciousAccountTransfersService {

    private final SuspiciousAccountTransfersRepository satRepository;
    private final SuspiciousAccountTransfersMapper satMapper;
    private SuspiciousAccountTransfers sat;

    /**
     * Добавляет новую запись о подозрительном переводе.
     *
     * @param satDto объект {@link SuspiciousAccountTransfersDto}, представляющий подозрительный перевод.
     */
    @Override
    @Auditable(action = Action.CREATE, entityType = SUSPICIOUS_ACCOUNT_TRANSFERS)
    public SuspiciousAccountTransfersDto add(SuspiciousAccountTransfersDto satDto) {
        sat = satMapper.toEntity(satDto);
        satRepository.save(sat);
        return satMapper.toDto(sat);
    }

    /**
     * Возвращает запись о подозрительном переводе по его идентификатору.
     *
     * @param id идентификатор подозрительного перевода.
     * @return объект {@link SuspiciousAccountTransfersDto}, представляющий подозрительный перевод.
     * @throws NotFoundSuspiciousAccountTransfersException если запись с указанным идентификатором не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public SuspiciousAccountTransfersDto get(Long id) {
        return satMapper.toDto(satRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id)));
    }

    /**
     * Обновляет существующую запись о подозрительном переводе.
     *
     * @param id  идентификатор обновляемой записи.
     * @param satDto объект {@link SuspiciousAccountTransfersDto}, содержащий обновленные данные.
     */
    @Override
    @Auditable(action = Action.UPDATE, entityType = SUSPICIOUS_ACCOUNT_TRANSFERS)
    public SuspiciousAccountTransfersDto update(Long id, SuspiciousAccountTransfersDto satDto) {
        sat = satRepository.findById(id).orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
        satMapper.updateExisting(sat, satDto);
        satRepository.save(sat);
        return satMapper.toDto(sat);
    }

    /**
     * Удаляет запись о подозрительном переводе по его идентификатору.
     *
     * @param id идентификатор удаляемой записи.
     */
    @Override
    public void remove(Long id) {
        if (!satRepository.existsById(id)) {
            throw new NotFoundSuspiciousAccountTransfersException(id);
        }
        satRepository.deleteById(id);
    }

    /**
     * Возвращает список всех записей о подозрительных переводах.
     *
     * @return список объектов {@link SuspiciousAccountTransfersDto}, представляющих все подозрительные переводы.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuspiciousAccountTransfersDto> getAll(Pageable pageable) {
        return satRepository.findAll(pageable)
                .map(satMapper::toDto);
    }
}
