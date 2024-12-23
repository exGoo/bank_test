package com.bank.transfer.service;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PhoneTransferService {

    Optional<PhoneTransferDTO> getPhoneTransferById(Long id);

    List<PhoneTransferDTO> allPhoneTransfer();

    PhoneTransfer savePhoneTransfer(PhoneTransferDTO phoneTransferDTO);

    PhoneTransfer updatePhoneTransferById(PhoneTransferDTO phoneTransferDTO, long id);

    void deletePhoneTransfer(Long id);
}
