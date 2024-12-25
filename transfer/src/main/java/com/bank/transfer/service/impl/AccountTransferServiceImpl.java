package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.AccountTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountTransferServiceImpl implements AccountTransferService {
    private final String metodCompl = "метод успешно выполнен";
    private final AccountTransferRepository accountTransferRepository;
    private final AccountTransferMapper mapper;

    @Autowired
    public AccountTransferServiceImpl(AccountTransferRepository accountTransferRepository,
                                      AccountTransferMapper mapper) {
        this.accountTransferRepository = accountTransferRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountTransferDTO> getAccountTransferById(Long id) {
        log.info("вызов метода getAccountTransferById");
        final Optional<AccountTransfer> accountTransfer = accountTransferRepository.findById(id);
        log.info(metodCompl);
        log.info("получение  AccountTransfer по ID {}", id);
        return accountTransfer.map(mapper::accountTransferToAccountTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<AccountTransferDTO> allAccountTransfer() {
        log.info("вызов метода allAccountTransfer");
        final List<AccountTransfer> accountTransferList = accountTransferRepository.findAll();
        log.info(metodCompl);
        return mapper.accountTransferListToDTOList(accountTransferList);
    }


    @Override
    @Transactional
    public AccountTransfer saveAccountTransfer(AccountTransferDTO accountTransferDTO) {
        log.info(metodCompl);
        log.info("Save AccountTransfer");
        return accountTransferRepository
                .save(mapper.accountTransferDTOToAccountTransfer(accountTransferDTO));
    }


    @Override
    @Transactional
    public AccountTransfer updateAccountTransferById(AccountTransferDTO accountTransferDTO, long id) {
        final AccountTransfer existingAccountTransfer = accountTransferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountTransfer not found for id: " + id));

        existingAccountTransfer.setAccountNumber(accountTransferDTO.getAccountNumber());
        existingAccountTransfer.setAmount(accountTransferDTO.getAmount());
        existingAccountTransfer.setPurpose(accountTransferDTO.getPurpose());
        existingAccountTransfer.setAccountDetailsId(accountTransferDTO.getAccountDetailsId());

        log.info(metodCompl);
        log.info("Updating accountTransfer with id: {}", id);
        return accountTransferRepository.save(existingAccountTransfer);
    }

    @Override
    @Transactional
    public void deleteAccountTransfer(Long id) {
        log.info("Delete AccountTransfer  id={}", id);
        accountTransferRepository.deleteById(id);
        log.info("удаление выполнено");
    }
}
