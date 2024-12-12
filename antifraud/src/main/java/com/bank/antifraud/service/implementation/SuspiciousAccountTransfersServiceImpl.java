 package com.bank.antifraud.service.implementation;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

    /**
     * Добавляет новую запись о подозрительном переводе.
     *
     * @param satDto объект {@link SuspiciousAccountTransfersDto}, представляющий подозрительный перевод.
     */
    @Override
    public void add(SuspiciousAccountTransfersDto satDto) {
        satRepository.save(satMapper.toEntity(satDto));
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
    public void update(Long id, SuspiciousAccountTransfersDto satDto) {
        get(id);
        SuspiciousAccountTransfers sat = satMapper.update(satDto);
        sat.setId(id);
        satRepository.save(sat);
    }

    /**
     * Удаляет запись о подозрительном переводе по его идентификатору.
     *
     * @param id идентификатор удаляемой записи.
     */
    @Override
    public void remove(Long id) {
        get(id);
        satRepository.deleteById(id);
    }

    /**
     * Возвращает список всех записей о подозрительных переводах.
     *
     * @return список объектов {@link SuspiciousAccountTransfersDto}, представляющих все подозрительные переводы.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousAccountTransfersDto> getAll() {
        return satMapper.toDtoList(satRepository.findAll());
    }
}
