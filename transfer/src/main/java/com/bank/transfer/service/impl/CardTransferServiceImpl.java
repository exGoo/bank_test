package com.bank.transfer.service.impl;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
import com.bank.transfer.service.CardTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CardTransferServiceImpl implements CardTransferService {
    private final String metodCompl = "метод успешно выполнен";

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
        log.info("вызов метода getCardTransferById");
        final Optional<CardTransfer> cardTransfer = cardTransferRepository.findById(id);
        log.info(metodCompl);
        log.info("получение  cardTransfer по ID {}", id);
        return cardTransfer.map(mapper::cardTransferToCardTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<CardTransferDTO> allCardTransfer() {
        log.info("вызов метода allCardTransfer");
        final List<CardTransfer> cardTransferList = cardTransferRepository.findAll();
        log.info(metodCompl);
        return mapper.cardTransferListToDTOList(cardTransferList);
    }


    @Override
    @Transactional
    public CardTransfer saveCardTransfer(CardTransferDTO cardTransferDTO) {
        log.info(metodCompl);
        log.info("Save cardTransfer");
        return cardTransferRepository
                .save(mapper.cardTransferDTOToCardTransfer(cardTransferDTO));
    }


    @Override
    @Transactional
    public CardTransfer updateCardTransferById(CardTransferDTO cardTransferDTO, long id) {

        final CardTransfer existingCardTransfer = cardTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CardTransfer not found for id: " + id));

        existingCardTransfer.setCardNumber(cardTransferDTO.getCardNumber());
        existingCardTransfer.setAmount(cardTransferDTO.getAmount());
        existingCardTransfer.setPurpose(cardTransferDTO.getPurpose());
        existingCardTransfer.setAccountDetailsId(cardTransferDTO.getAccountDetailsId());
        log.info(metodCompl);
        log.info("Updating cardTransfer with id: {}", id);
        return cardTransferRepository.save(existingCardTransfer);
    }

    @Override
    @Transactional
    public void deleteCardTransfer(Long id) {
        log.info("Delete CardTransfer  id={}", id);
        cardTransferRepository.deleteById(id);
        log.info("удаление выполнено");
    }
}
