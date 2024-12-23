package com.bank.transfer.service.impl;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
import com.bank.transfer.service.CardTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CardTransferServiceImpl implements CardTransferService {
    private final CardTransferRepository cardTransferRepository;
    private final CardTransferMapper mapper;

    @Autowired
    public CardTransferServiceImpl(CardTransferRepository cardTransferRepository, CardTransferMapper mapper) {
        this.cardTransferRepository = cardTransferRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CardTransferDTO> getCardTransferById(Long id) {
        final Optional<CardTransfer> cardTransfer = cardTransferRepository.findById(id);
//        return mapper.cardTransferToCardTransferDTO(cardTransfer);
        return cardTransfer.map(mapper::cardTransferToCardTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<CardTransferDTO> allCardTransfer() {
        final List<CardTransfer> cardTransferList = cardTransferRepository.findAll();
        return mapper.cardTransferListToDTOList(cardTransferList);
    }


    @Override
    @Transactional
    public CardTransfer saveCardTransfer(CardTransferDTO cardTransferDTO) {
        return cardTransferRepository
                .save(mapper.cardTransferDTOToCardTransfer(cardTransferDTO));
    }


    @Override
    @Transactional
    public CardTransfer updateCardTransferById(CardTransferDTO cardTransferDTO, long id) {
        if (cardTransferDTO == null) {
            throw new IllegalArgumentException("CardTransferDTO to update cannot be null");
        }

        final Optional<CardTransferDTO> optionalCardTransferDTO = getCardTransferById(id);

        final CardTransferDTO cardTransferDTO3 = optionalCardTransferDTO.orElseThrow(() ->
                new EntityNotFoundException("CardTransfer not found for id: " + id));


        cardTransferDTO3.setCardNumber(cardTransferDTO.getCardNumber());
        cardTransferDTO3.setAmount(cardTransferDTO.getAmount());
        cardTransferDTO3.setPurpose(cardTransferDTO.getPurpose());
        cardTransferDTO3.setAccountDetailsId(cardTransferDTO.getAccountDetailsId());

        return mapper.cardTransferDTOToCardTransfer(cardTransferDTO3);
    }

    @Override
    @Transactional
    public void deleteCardTransfer(Long id) {
        cardTransferRepository.deleteById(id);
    }
}
