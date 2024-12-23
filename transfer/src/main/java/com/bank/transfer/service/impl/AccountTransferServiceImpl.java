package com.bank.transfer.service.impl;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.AccountTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountTransferServiceImpl implements AccountTransferService {
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
        final Optional<AccountTransfer> accountTransfer = accountTransferRepository.findById(id);
        return accountTransfer.map(mapper::accountTransferToAccountTransferDTO);

    }


    @Override
    @Transactional(readOnly = true)
    public List<AccountTransferDTO> allAccountTransfer() {
        final List<AccountTransfer> accountTransferList = accountTransferRepository.findAll();
        return mapper.accountTransferListToDTOList(accountTransferList);
    }


    @Override
    @Transactional
    public AccountTransfer saveAccountTransfer(AccountTransferDTO accountTransferDTO) {
        return accountTransferRepository
                .save(mapper.accountTransferDTOToAccountTransfer(accountTransferDTO));
    }


    @Override
    @Transactional
    public AccountTransfer updateAccountTransferById(AccountTransferDTO accountTransferDTO, long id) {
        if (accountTransferDTO == null) {
            throw new IllegalArgumentException("AccountTransferDTO to update cannot be null");
        }

        final Optional<AccountTransferDTO> optionalAccountTransfer = getAccountTransferById(id);
        final AccountTransferDTO accountTransfer = optionalAccountTransfer.orElseThrow(() ->
                new EntityNotFoundException("AccountTransfer not found for id: " + id));

        accountTransfer.setAccountNumber(accountTransferDTO.getAccountNumber());
        accountTransfer.setAmount(accountTransferDTO.getAmount());
        accountTransfer.setPurpose(accountTransferDTO.getPurpose());
        accountTransfer.setAccountDetailsId(accountTransferDTO.getAccountDetailsId());

        return mapper.accountTransferDTOToAccountTransfer(accountTransfer);
    }

    @Override
    @Transactional
    public void deleteAccountTransfer(Long id) {
        accountTransferRepository.deleteById(id);
    }
}
