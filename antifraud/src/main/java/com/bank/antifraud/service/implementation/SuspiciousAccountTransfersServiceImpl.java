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

    private final SuspiciousAccountTransfersRepository satr;

    @Override
    public void add(SuspiciousAccountTransfers sat) {
        satr.save(sat);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousAccountTransfers get(Long id) {
        return satr.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id));
    }

    @Override
    public void update(SuspiciousAccountTransfers sat) {
        if (!satr.existsById(sat.getId())) {
            throw new NotFoundSuspiciousAccountTransfersException(sat.getId());
        }
        satr.save(sat);
    }

    @Override
    public void remove(Long id) {
        if (!satr.existsById(id)) {
            throw new NotFoundSuspiciousAccountTransfersException(id);
        }
        satr.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousAccountTransfers> getAll() {
        return satr.findAll();
    }
}
