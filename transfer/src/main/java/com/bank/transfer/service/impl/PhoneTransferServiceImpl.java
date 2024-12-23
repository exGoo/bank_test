package com.bank.transfer.service.impl;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.PhoneTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneTransferServiceImpl implements PhoneTransferService {
    private final PhoneTransferRepository phoneTransferRepository;
    private final PhoneTransferMapper mapper;

    @Autowired
    public PhoneTransferServiceImpl(PhoneTransferRepository phoneTransferRepository, PhoneTransferMapper mapper) {
        this.phoneTransferRepository = phoneTransferRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneTransferDTO> getPhoneTransferById(Long id) {
        final Optional<PhoneTransfer> phoneTransfer = phoneTransferRepository.findById(id);
//        return mapper.phoneTransferToPhoneTransferDTO(phoneTransfer);
        return phoneTransfer.map(mapper::phoneTransferToPhoneTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<PhoneTransferDTO> allPhoneTransfer() {
        final List<PhoneTransfer> phoneTransferList = phoneTransferRepository.findAll();
        return mapper.phoneTransferListToDTOList(phoneTransferList);
    }


    @Override
    @Transactional
    public PhoneTransfer savePhoneTransfer(PhoneTransferDTO phoneTransferDTO) {
        return phoneTransferRepository
                .save(mapper.phoneTransferDTOToPhoneTransfer(phoneTransferDTO));
    }


    @Override
    @Transactional
    public PhoneTransfer updatePhoneTransferById(PhoneTransferDTO phoneTransferDTO, long id) {
        if (phoneTransferDTO == null) {
            throw new IllegalArgumentException("PhoneTransferDTO to update cannot be null");
        }

        final Optional<PhoneTransferDTO> optionalPhoneTransferDTO = getPhoneTransferById(id);

        final PhoneTransferDTO phoneTransfer = optionalPhoneTransferDTO.orElseThrow(() ->
                new EntityNotFoundException("PhoneTransfer not found for id: " + id));


        phoneTransfer.setPhoneNumber(phoneTransferDTO.getPhoneNumber());
        phoneTransfer.setAmount(phoneTransferDTO.getAmount());
        phoneTransfer.setPurpose(phoneTransferDTO.getPurpose());
        phoneTransfer.setAccountDetailsId(phoneTransferDTO.getAccountDetailsId());

        return mapper.phoneTransferDTOToPhoneTransfer(phoneTransfer);
    }

    @Override
    @Transactional
    public void deletePhoneTransfer(Long id) {
        phoneTransferRepository.deleteById(id);
    }
}
