package com.bank.antifraud.service.implementation;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.NotFoundSuspiciousPhoneTransfersException;
import com.bank.antifraud.mapper.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuspiciousPhoneTransfersServiceImpl implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository sptRepository;
    private final SuspiciousPhoneTransfersMapper sptMapper;
    private SuspiciousPhoneTransfers spt;

    @Override
    public SuspiciousPhoneTransfersDto add(SuspiciousPhoneTransfersDto sptDto) {
        spt = sptMapper.toEntity(sptDto);
        sptRepository.save(spt);
        return sptMapper.toDto(spt);
    }

    @Override
    public SuspiciousPhoneTransfersDto get(Long id) {
        return sptMapper.toDto(sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousPhoneTransfersException(id)));
    }

    @Override
    public void update(Long id, SuspiciousPhoneTransfersDto sptDto) {
        spt = sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousPhoneTransfersException(id));
        sptMapper.updateExisting(spt, sptDto);
        sptRepository.save(spt);
    }

    @Override
    public void remove(Long id) {
        if (!sptRepository.existsById(id)) {
            throw new NotFoundSuspiciousPhoneTransfersException(id);
        }
        sptRepository.deleteById(id);
    }

    @Override
    public Page<SuspiciousPhoneTransfersDto> getAll(Pageable pageable) {
        return sptRepository.findAll(pageable)
                .map(sptMapper::toDto);
    }
}
