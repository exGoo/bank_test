package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousAccountTransfersServiceImpl implements SuspiciousAccountTransfersService {

    private final SuspiciousAccountTransfersRepository satRepository;

    @Override
    public void add(SuspiciousAccountTransfers sat) {
        satRepository.save(sat);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousAccountTransfers get(Long id) {
        return satRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
    }

    @Override
    public void update(Long id, SuspiciousAccountTransfers sat) {
        final SuspiciousAccountTransfers currentSat = get(id);
        currentSat.setIsBlocked(sat.getIsBlocked());
        currentSat.setIsSuspicious(sat.getIsSuspicious());
        currentSat.setBlockedReason(sat.getBlockedReason());
        currentSat.setSuspiciousReason(sat.getSuspiciousReason());
        satRepository.save(currentSat);
    }

    @Override
    public void remove(Long id) {
        satRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousAccountTransfers> getAll() {
        return satRepository.findAll();
    }

}
