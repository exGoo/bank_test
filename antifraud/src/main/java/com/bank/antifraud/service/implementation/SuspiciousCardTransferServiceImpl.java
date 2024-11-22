package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.model.SuspiciousCardTransfer;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctRepository;

    private final AuditRepository auditRepository;

    @Override
    public void add(SuspiciousCardTransfer sct) {
        sctRepository.save(sct);
    }

    @Override
    public SuspiciousCardTransfer get(Long id) {
        return sctRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
    }

    @Override
    public void update(Long id, SuspiciousCardTransfer sct) {
        final SuspiciousCardTransfer currentSat = get(id);
        currentSat.setIsBlocked(sct.getIsBlocked());
        currentSat.setIsSuspicious(sct.getIsSuspicious());
        currentSat.setBlockedReason(sct.getBlockedReason());
        currentSat.setSuspiciousReason(sct.getSuspiciousReason());
        sctRepository.save(currentSat);
    }

    @Override
    public void remove(Long id) {
        sctRepository.deleteById(id);
    }

    @Override
    public List<SuspiciousCardTransfer> getAll() {
        return sctRepository.findAll();
    }

}
