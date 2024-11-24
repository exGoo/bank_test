package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousPhoneTransfersServiceImpl implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository sptRepository;

    @Override
    public void add(SuspiciousPhoneTransfers sat) {
        sptRepository.save(sat);
    }

    @Override
    public SuspiciousPhoneTransfers get(Long id) {
        return sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
    }

    @Override
    public void update(Long id, SuspiciousPhoneTransfers sat) {
        final SuspiciousPhoneTransfers currentSat = get(id);
        currentSat.setIsBlocked(sat.getIsBlocked());
        currentSat.setIsSuspicious(sat.getIsSuspicious());
        currentSat.setBlockedReason(sat.getBlockedReason());
        currentSat.setSuspiciousReason(sat.getSuspiciousReason());
        sptRepository.save(currentSat);
    }

    @Override
    public void remove(Long id) {
        sptRepository.deleteById(id);
    }

    @Override
    public List<SuspiciousPhoneTransfers> getAll() {
        return sptRepository.findAll();
    }
}
