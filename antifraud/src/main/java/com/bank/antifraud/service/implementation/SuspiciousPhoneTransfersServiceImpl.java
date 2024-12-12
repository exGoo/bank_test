package com.bank.antifraud.service.implementation;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.mapper.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousPhoneTransfersServiceImpl implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository sptRepository;
    private final SuspiciousPhoneTransfersMapper sptMapper;

    @Override
    public void add(SuspiciousPhoneTransfersDto spt) {
        sptRepository.save(sptMapper.toEntity(spt));
    }

    @Override
    public SuspiciousPhoneTransfersDto get(Long id) {
        return sptMapper.toDto(sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousAccountTransfersException(id)));
    }

    @Override
    public void update(Long id, SuspiciousPhoneTransfersDto spt) {
        get(id);
        final SuspiciousPhoneTransfers currentSat = sptMapper.update(spt);
        currentSat.setId(id);
        sptRepository.save(currentSat);
    }

    @Override
    public void remove(Long id) {
        get(id);
        sptRepository.deleteById(id);
    }

    @Override
    public List<SuspiciousPhoneTransfersDto> getAll() {
        return sptMapper.toDtoList(sptRepository.findAll());
    }
}
