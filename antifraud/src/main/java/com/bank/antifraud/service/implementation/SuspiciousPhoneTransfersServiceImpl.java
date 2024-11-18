package com.bank.antifraud.service.implementation;

import com.bank.antifraud.exception.NotFoundSuspiciousPhoneTransfersException;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousPhoneTransfersServiceImpl implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository sptr;

    @Override
    public void add(SuspiciousPhoneTransfers audit) {
        sptr.save(audit);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousPhoneTransfers get(Long id) {
        return sptr.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousPhoneTransfersException(id));
    }

    @Override
    public void update(SuspiciousPhoneTransfers audit) {
        if (!sptr.existsById(audit.getId())) {
            throw new NotFoundSuspiciousPhoneTransfersException(audit.getId());
        }
        sptr.save(audit);
    }

    @Override
    public void remove(Long id) {
        if (!sptr.existsById(id)) {
            throw new NotFoundSuspiciousPhoneTransfersException(id);
        }
        sptr.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuspiciousPhoneTransfers> getAll() {
        return sptr.findAll();
    }

}
