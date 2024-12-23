package com.bank.transfer.service;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountTransferService {

    Optional<AccountTransferDTO> getAccountTransferById(Long id);

    List<AccountTransferDTO> allAccountTransfer();

    AccountTransfer saveAccountTransfer(AccountTransferDTO accountTransferDTO);

    AccountTransfer updateAccountTransferById(AccountTransferDTO accountTransferDTO, long id);

    void deleteAccountTransfer(Long id);
}
