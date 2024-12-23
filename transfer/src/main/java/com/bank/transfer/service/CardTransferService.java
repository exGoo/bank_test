package com.bank.transfer.service;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CardTransferService {

    Optional<CardTransferDTO> getCardTransferById(Long id);

    List<CardTransferDTO> allCardTransfer();

    CardTransfer saveCardTransfer(CardTransferDTO cardTransferDTO);

    CardTransfer updateCardTransferById(CardTransferDTO cardTransferDTO, long id);

    void deleteCardTransfer(Long id);
}
