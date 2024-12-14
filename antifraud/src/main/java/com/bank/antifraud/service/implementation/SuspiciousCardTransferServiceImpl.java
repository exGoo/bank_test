package com.bank.antifraud.service.implementation;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.NotFoundSuspiciousCardTransferException;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctRepository;
    private final SuspiciousCardTransferMapper sctMapper;
    private SuspiciousCardTransfer sct;

    @Override
    public SuspiciousCardTransferDto add(SuspiciousCardTransferDto sctDto) {
        sct = sctMapper.toEntity(sctDto);
        sctRepository.save(sct);
        return sctMapper.toDto(sct);
    }

    @Override
    public SuspiciousCardTransferDto get(Long id) {
        return sctMapper.toDto(sctRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousCardTransferException(id)));
    }

    @Override
    public void update(Long id, SuspiciousCardTransferDto sctDto) {
        sct = sctRepository.findById(id).orElseThrow(() -> new NotFoundSuspiciousCardTransferException(id));
        sctMapper.updateExisting(sct, sctDto);
        sctRepository.save(sct);
    }

    @Override
    public void remove(Long id) {
        if (!sctRepository.existsById(id)) {
            throw new NotFoundSuspiciousCardTransferException(id);
        }
        sctRepository.deleteById(id);
    }

    @Override
    public List<SuspiciousCardTransferDto> getAll() {
        return sctMapper.toDtoList(sctRepository.findAll());
    }

}
