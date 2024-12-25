package com.bank.transfer.service.impl;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.PhoneTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhoneTransferServiceImpl implements PhoneTransferService {
    private final String metodCompl = "метод успешно выполнен";
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
        log.info("вызов метода getCardTransferById");
        final Optional<PhoneTransfer> phoneTransfer = phoneTransferRepository.findById(id);
        log.info(metodCompl);
        log.info("получение  phoneTransfer по ID {}", id);
        return phoneTransfer.map(mapper::phoneTransferToPhoneTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<PhoneTransferDTO> allPhoneTransfer() {
        log.info("вызов метода allPhoneTransfer");
        final List<PhoneTransfer> phoneTransferList = phoneTransferRepository.findAll();
        log.info(metodCompl);
        return mapper.phoneTransferListToDTOList(phoneTransferList);
    }


    @Override
    @Transactional
    public PhoneTransfer savePhoneTransfer(PhoneTransferDTO phoneTransferDTO) {
        log.info(metodCompl);
        log.info("Save PhoneTransfer");
        return phoneTransferRepository
                .save(mapper.phoneTransferDTOToPhoneTransfer(phoneTransferDTO));
    }


    @Override
    @Transactional
    public PhoneTransfer updatePhoneTransferById(PhoneTransferDTO phoneTransferDTO, long id) {
        final PhoneTransfer existingPhoneTransfer = phoneTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PhoneTransfer not found for id: " + id));

        existingPhoneTransfer.setPhoneNumber(phoneTransferDTO.getPhoneNumber());
        existingPhoneTransfer.setAmount(phoneTransferDTO.getAmount());
        existingPhoneTransfer.setPurpose(phoneTransferDTO.getPurpose());
        existingPhoneTransfer.setAccountDetailsId(phoneTransferDTO.getAccountDetailsId());

        log.info(metodCompl);
        log.info("Updating PhoneTransfer with id: {}", id);
        return phoneTransferRepository.save(existingPhoneTransfer);
    }

    @Override
    @Transactional
    public void deletePhoneTransfer(Long id) {
        log.info("Delete PhoneTransfer  id={}", id);
        phoneTransferRepository.deleteById(id);
        log.info("удаление выполнено");
    }
}
