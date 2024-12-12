package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.util.antifraudSystem.CardTransferAntifraud;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardTransferAntifraudImpl implements CardTransferAntifraud {

    private final TransferServiceClient transferService;

    @Override
    public SuspiciousCardTransfer checkTransfer(SuspiciousCardTransfer sct) {
        final CardTransferDto transfer = getTransferById(sct.getCardTransferId());
        final List<CardTransferDto> transfers = getTransfersByCardNumberAndAccountDetailsId(
                transfer.getCardNumber(),
                transfer.getAccountDetailsId()
        );
        final BigDecimal currentAmount = transfer.getAmount();
        final BigDecimal averageAmount = getAverageAmountOfTransfers(
                transfers.stream().map(CardTransferDto::getAmount),
                BigDecimal.valueOf(transfers.size())
        );
        if (isSuspicious(currentAmount, averageAmount) && averageAmount.compareTo(BigDecimal.ZERO) > 0) {
            sct.setIsSuspicious(true);
            sct.setSuspiciousReason("Suspicious amount of transaction");
            if (isMustBlocked(currentAmount, averageAmount)) {
                sct.setIsBlocked(true);
                sct.setBlockedReason("Suspicion of theft of personal funds");
            }
        }
        return sct;
    }

    @Override
    public CardTransferDto getTransferById(Long id) {
        log.info("Invoke getPhoneTransferById method with id: {}", id);
        try {
            return transferService.getCardTransferById(id);
        } catch (FeignException e) {
            log.error("getPhoneTransferById method with id: {} throw FeignException with cause {}, message {}",
                    id, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CardTransferDto> getTransfersByCardNumberAndAccountDetailsId(Long cardNumber, Long accountDetailsId) {
        log.info("Invoke getTransfersByCardNumberAndAccountDetailsId method " +
                "with accountNumber: {} and accountDetailsId: {}", cardNumber, accountDetailsId);
        List<CardTransferDto> transfers = List.of();
        try {
            transfers = transferService.getCardTransferByCardNumberAndAccountDetailsId(cardNumber,
                    accountDetailsId);
        } catch (FeignException.NotFound ignore) {
            log.info("CardTransfers not found with accountId: {} and accountNumber: {}",
                    cardNumber, accountDetailsId);
        } catch (FeignException e) {
            log.error("getTransfersByAccountNumberAndAccountDetailsId method " +
                            "with accountNumber: {} and accountDetailsId: {}" +
                            "throw FeignException with cause {}, message {}",
                    cardNumber, accountDetailsId, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
        return transfers;
    }
}
