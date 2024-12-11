package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.Antifraud;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountAntifraud implements Antifraud<SuspiciousAccountTransfers> {

    private final TransferServiceClient transferService;

    @Override
    public SuspiciousAccountTransfers checkTransaction(SuspiciousAccountTransfers model) {
        final AccountTransferDto transfer = getTransferById(model.getAccountTransferId());
        final int averageAmount = getAverageAmountsByAccountIdAndReceiverId(transfer.getAccountNumber(),
                transfer.getAccountDetailsId());
        final int currentAmount = transfer.getAmount();
        if(isSuspicious(currentAmount, averageAmount)) {
            model.setIsSuspicious(true);
            model.setSuspiciousReason("Suspicious amount of transaction");
            if(isMustBlocked(currentAmount, averageAmount)) {
                model.setIsBlocked(true);
                model.setBlockedReason("Suspicion of theft of personal funds");
            }
        }
        return model;
    }

    private AccountTransferDto getTransferById(Long id) {
        return transferService.getAccountTransfer(id)
                .orElseThrow(() -> new NotFoundException("AccountTransfer not found with id: " + id));
    }

    private int getAverageAmountsByAccountIdAndReceiverId(Long accountNumber, Long accountDetailsId) {
        final List<AccountTransferDto> accountTransfers = transferService
                .getAccountTransferByAccountNumberAndAccountDetailsId(accountNumber, accountNumber);
        if (accountTransfers.isEmpty()) {
            throw new NotFoundException(String.format(
                    "AccountTransfers not found with accountId: %d and accountNumber: %d",
                    accountNumber, accountNumber
            ));
        }
        return accountTransfers.stream()
                .mapToInt(AccountTransferDto::getAmount)
                .sum() / accountTransfers.size();
    }

}
