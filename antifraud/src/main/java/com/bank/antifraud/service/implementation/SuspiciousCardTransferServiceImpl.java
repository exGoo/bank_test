package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFounSuspiciousCardTransferException;
import com.bank.antifraud.model.SuspiciousCardTransfer;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctr;

    @Override
    public void add(SuspiciousCardTransfer scd) {
        sctr.save(scd);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousCardTransfer get(Long id) {
        return sctr.findById(id)
                .orElseThrow(() -> new NotFounSuspiciousCardTransferException(id));
    }

    @Override
    public void update(SuspiciousCardTransfer scd) {
        if (!sctr.existsById(scd.getId())) {
            throw new NotFounSuspiciousCardTransferException(scd.getId());
        }
        sctr.save(scd);
    }

    @Override
    public void remove(Long id) {
        if (!sctr.existsById(id)) {
            throw new NotFounSuspiciousCardTransferException(id);
        }
        sctr.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousCardTransfer> getAll() {
        return sctr.findAll();
    }
}
