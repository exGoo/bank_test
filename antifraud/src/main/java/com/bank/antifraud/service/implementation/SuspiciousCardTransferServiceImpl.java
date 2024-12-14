package com.bank.antifraud.service.implementation;

import com.bank.antifraud.annotation.Auditable;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.NotFoundSuspiciousCardTransferException;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.bank.antifraud.annotation.Auditable.EntityType;
import static com.bank.antifraud.annotation.Auditable.Action;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctRepository;
    private final SuspiciousCardTransferMapper sctMapper;
    private SuspiciousCardTransfer sct;

    @Override
    @Auditable(action = Action.CREATE, entityType = EntityType.SUSPICIOUS_CARD_TRANSFER)
    public SuspiciousCardTransferDto add(SuspiciousCardTransferDto sctDto) {
        sct = sctMapper.toEntity(sctDto);
        sctRepository.save(sct);
        return sctMapper.toDto(sct);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousCardTransferDto get(Long id) {
        return sctMapper.toDto(sctRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousCardTransferException(id)));
    }

    @Override
    @Auditable(action = Action.UPDATE, entityType = EntityType.SUSPICIOUS_CARD_TRANSFER)
    public SuspiciousCardTransferDto update(Long id, SuspiciousCardTransferDto sctDto) {
        sct = sctRepository.findById(id).orElseThrow(() -> new NotFoundSuspiciousCardTransferException(id));
        sctMapper.updateExisting(sct, sctDto);
        sctRepository.save(sct);
        return sctMapper.toDto(sct);
    }

    @Override
    public void remove(Long id) {
        if (!sctRepository.existsById(id)) {
            throw new NotFoundSuspiciousCardTransferException(id);
        }
        sctRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SuspiciousCardTransferDto> getAll(Pageable pageable) {
        return sctRepository.findAll(pageable)
                .map(sctMapper::toDto);
    }

}
