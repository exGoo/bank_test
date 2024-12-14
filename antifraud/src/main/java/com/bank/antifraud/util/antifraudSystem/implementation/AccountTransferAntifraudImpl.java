package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.AccountTransferAntifraud;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountTransferAntifraudImpl implements AccountTransferAntifraud {

    private final TransferServiceClient transferService;

    @Override
    public SuspiciousAccountTransfers checkAccountTransfer(SuspiciousAccountTransfers sat) {
        log.info("Invoke checkAccountTransfer method with SuspiciousAccountTransfers: {}", sat);
        final long accountTransferId = sat.getAccountTransferId();
        final AccountTransferDto transfer = getAccountTransferById(sat.getAccountTransferId());
        final List<AccountTransferDto> transfers = getAccountTransfersByAccountNumberAndAccountDetailsId(
                transfer.getAccountNumber(),
                transfer.getAccountDetailsId()
        );
        final BigDecimal currentAmount = transfer.getAmount();
        final BigDecimal averageAmount = getAverageAmountOfTransfers(
                        transfers.stream().map(AccountTransferDto::getAmount),
                        BigDecimal.valueOf(transfers.size())
        );
        if (isSuspicious(currentAmount, averageAmount) && averageAmount.compareTo(BigDecimal.ZERO) > 0) {
            log.info("AccountTransfer with id: {} is suspicious", accountTransferId);
            sat.setIsSuspicious(true);
            sat.setSuspiciousReason("Suspicious amount of transaction");
            if (isMustBlocked(currentAmount, averageAmount)) {
                log.info("AccountTransfer with id: {} blocked", accountTransferId);
                sat.setIsBlocked(true);
                sat.setBlockedReason("Suspicion of theft of personal funds");
            }
        }
        return sat;
    }

    @Override
    public AccountTransferDto getAccountTransferById(Long id) {
        log.info("Invoke getAccountTransferById method with id: {}", id);
        try {
            return transferService.getAccountTransferById(id);
        } catch (FeignException e) {
            log.error("getAccountTransferById method with id: {} throw FeignException with cause: {}, message: {}",
                    id, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AccountTransferDto> getAccountTransfersByAccountNumberAndAccountDetailsId(Long accountNumber,
                                                                                          Long accountDetailsId) {
        log.info("Invoke getAccountTransfersByAccountNumberAndAccountDetailsId method " +
                "with accountNumber: {} and accountDetailsId: {}", accountNumber, accountDetailsId);
        List<AccountTransferDto> transfers = List.of();
        try {
            transfers = transferService.getAccountTransferByAccountNumberAndAccountDetailsId(accountNumber,
                    accountDetailsId);
        } catch (FeignException.NotFound ignore) {
            log.info("AccountTransfers not found with account number: {} and account details id: {}",
                    accountNumber, accountDetailsId);
        } catch (FeignException e) {
            log.error("getAccountTransfersByAccountNumberAndAccountDetailsId method with accountNumber: {} " +
                            "and accountDetailsId: {} throw FeignException with cause: {}, message: {}",
                    accountNumber, accountDetailsId, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
        return transfers;
    }
}
