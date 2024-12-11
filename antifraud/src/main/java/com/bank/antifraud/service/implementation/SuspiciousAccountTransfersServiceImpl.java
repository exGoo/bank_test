package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
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

    /**
     * Добавляет новую запись о подозрительном переводе.
     *
     * @param sat объект {@link SuspiciousAccountTransfers}, представляющий подозрительный перевод.
     */
    @Override
    public void add(SuspiciousAccountTransfers sat) {
        satRepository.save(sat);
    }

    /**
     * Возвращает запись о подозрительном переводе по его идентификатору.
     *
     * @param id идентификатор подозрительного перевода.
     * @return объект {@link SuspiciousAccountTransfers}, представляющий подозрительный перевод.
     * @throws NotFoundSuspiciousAccountTransfersException если запись с указанным идентификатором не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public SuspiciousAccountTransfers get(Long id) {
        return satRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
    }

    /**
     * Обновляет существующую запись о подозрительном переводе.
     *
     * @param id  идентификатор обновляемой записи.
     * @param sat объект {@link SuspiciousAccountTransfers}, содержащий обновленные данные.
     */
    @Override
    public void update(Long id, SuspiciousAccountTransfers sat) {
        final SuspiciousAccountTransfers currentSat = get(id);
        currentSat.setIsBlocked(sat.getIsBlocked());
        currentSat.setIsSuspicious(sat.getIsSuspicious());
        currentSat.setBlockedReason(sat.getBlockedReason());
        currentSat.setSuspiciousReason(sat.getSuspiciousReason());
        satRepository.save(currentSat);
    }

    /**
     * Удаляет запись о подозрительном переводе по его идентификатору.
     *
     * @param id идентификатор удаляемой записи.
     */
    @Override
    public void remove(Long id) {
        satRepository.deleteById(id);
    }

    /**
     * Возвращает список всех записей о подозрительных переводах.
     *
     * @return список объектов {@link SuspiciousAccountTransfers}, представляющих все подозрительные переводы.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousAccountTransfers> getAll() {
        return satRepository.findAll();
    }
}
