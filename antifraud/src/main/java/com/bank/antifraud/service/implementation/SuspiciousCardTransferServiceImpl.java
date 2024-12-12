package com.bank.antifraud.service.implementation;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousCardTransferServiceImpl implements SuspiciousCardTransferService {

    private final SuspiciousCardTransferRepository sctRepository;
    private final SuspiciousCardTransferMapper sctMapper;

    @Override
    public void add(SuspiciousCardTransferDto sctDto) {
        sctRepository.save(sctMapper.toEntity(sctDto));
    }

    @Override
    public SuspiciousCardTransferDto get(Long id) {
        return sctMapper.toDto(sctRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id)));
    }

    @Override
    public void update(Long id, SuspiciousCardTransferDto sctDto) {
        get(id);
        final SuspiciousCardTransfer sct = sctMapper.update(sctDto);
        sct.setId(id);
        sctRepository.save(sct);
    }

    @Override
    public void remove(Long id) {
        sctRepository.deleteById(id);
    }

    @Override
    public List<SuspiciousCardTransferDto> getAll() {
        return sctMapper.toDtoList(sctRepository.findAll());
    }

}
