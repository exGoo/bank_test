package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.Antifraud;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountTransferAntifraud implements Antifraud<SuspiciousAccountTransfers> {

    private final TransferServiceClient transferService;

    @Override
    public SuspiciousAccountTransfers checkTransaction(SuspiciousAccountTransfers entity) {
        AccountTransferDto transfer = null;
        int averageAmount = 0;
        long accountNumber = 0;
        long accountDetailsId = 0;
        int currentAmount = 0;
        try {
            transfer = getTransferById(entity.getAccountTransferId());
            accountNumber = transfer.getAccountNumber();
            accountDetailsId = transfer.getAccountDetailsId();
            currentAmount = transfer.getAmount();
            averageAmount = getAverageAmountsByAccountIdAndReceiverId(accountNumber, accountDetailsId);
        } catch (FeignException.NotFound ignore) {
                log.info("Transactions with account number {} and account details id {} are not available",
                        accountNumber, accountDetailsId);
        } catch (FeignException e) {
            log.error("An exception was thrown during check transaction with cause {}, message {}",
                    e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
        if (isSuspicious(currentAmount, averageAmount) && averageAmount != 0) {
            entity.setIsSuspicious(true);
            entity.setSuspiciousReason("Suspicious amount of transaction");
            if (isMustBlocked(currentAmount, averageAmount)) {
                entity.setIsBlocked(true);
                entity.setBlockedReason("Suspicion of theft of personal funds");
            }
        }
        return entity;
    }

    private AccountTransferDto getTransferById(Long id) throws FeignException {
        return Optional.ofNullable(transferService.getAccountTransfer(id)).orElseThrow(
                () -> new NotFoundException(String.format("AccountTransfer with id %d not found", id)));
    }

    private int getAverageAmountsByAccountIdAndReceiverId(Long accountNumber, Long accountDetailsId) throws FeignException {
        final List<AccountTransferDto> accountTransfers = transferService
                .getAccountTransferByAccountNumberAndAccountDetailsId(accountNumber, accountDetailsId);
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
