package com.bank.antifraud.service.implementation;

import com.bank.antifraud.annotation.Auditable;
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
import org.springframework.transaction.annotation.Transactional;
import static com.bank.antifraud.annotation.Auditable.Action;
import static com.bank.antifraud.annotation.Auditable.EntityType;

@Service
@Transactional
@RequiredArgsConstructor
public class SuspiciousPhoneTransfersServiceImpl implements SuspiciousPhoneTransfersService {

    private final SuspiciousPhoneTransfersRepository sptRepository;
    private final SuspiciousPhoneTransfersMapper sptMapper;
    private SuspiciousPhoneTransfers spt;

    @Override
    @Auditable(action = Action.CREATE, entityType = EntityType.SUSPICIOUS_PHONE_TRANSFERS)
    public SuspiciousPhoneTransfersDto add(SuspiciousPhoneTransfersDto sptDto) {
        spt = sptMapper.toEntity(sptDto);
        sptRepository.save(spt);
        return sptMapper.toDto(spt);
    }

    @Override
    @Transactional(readOnly = true)
    public SuspiciousPhoneTransfersDto get(Long id) {
        return sptMapper.toDto(sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousPhoneTransfersException(id)));
    }

    @Override
    @Auditable(action = Action.UPDATE, entityType = EntityType.SUSPICIOUS_PHONE_TRANSFERS)
    public SuspiciousPhoneTransfersDto update(Long id, SuspiciousPhoneTransfersDto sptDto) {
        spt = sptRepository.findById(id)
                .orElseThrow(() -> new NotFoundSuspiciousPhoneTransfersException(id));
        sptMapper.updateExisting(spt, sptDto);
        sptRepository.save(spt);
        return sptMapper.toDto(spt);
    }

    @Override
    public void remove(Long id) {
        if (!sptRepository.existsById(id)) {
            throw new NotFoundSuspiciousPhoneTransfersException(id);
        }
        sptRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SuspiciousPhoneTransfersDto> getAll(Pageable pageable) {
        return sptRepository.findAll(pageable)
                .map(sptMapper::toDto);
    }
}
