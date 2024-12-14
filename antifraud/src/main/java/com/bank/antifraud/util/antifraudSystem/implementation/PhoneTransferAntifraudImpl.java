package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.util.antifraudSystem.PhoneTransferAntifraud;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhoneTransferAntifraudImpl implements PhoneTransferAntifraud {

    private final TransferServiceClient transferService;

    @Override
    public SuspiciousPhoneTransfers checkPhoneTransfer(SuspiciousPhoneTransfers spt) {
        log.info("Invoke checkPhoneTransfer method with SuspiciousPhoneTransfers: {}", spt);
        final long phoneTransferId = spt.getPhoneTransferId();
        final PhoneTransferDto transfer = getPhoneTransferById(phoneTransferId);
        final List<PhoneTransferDto> transfers = getPhoneTransfersByCardNumberAndAccountDetailsId(
                transfer.getPhoneNumber(),
                transfer.getAccountDetailsId()
        );
        final BigDecimal currentAmount = transfer.getAmount();
        final BigDecimal averageAmount = getAverageAmountOfTransfers(
                transfers.stream().map(PhoneTransferDto::getAmount),
                BigDecimal.valueOf(transfers.size())
        );
        if (isSuspicious(currentAmount, averageAmount) && averageAmount.compareTo(BigDecimal.ZERO) > 0) {
            log.info("PhoneTransfer with id: {} is suspicious", phoneTransferId);
            spt.setIsSuspicious(true);
            spt.setSuspiciousReason("Suspicious amount of transaction");
            if (isMustBlocked(currentAmount, averageAmount)) {
                log.info("PhoneTransfer with id: {} blocked", phoneTransferId);
                spt.setIsBlocked(true);
                spt.setBlockedReason("Suspicion of theft of personal funds");
            }
        }
        return spt;
    }

    @Override
    public PhoneTransferDto getPhoneTransferById(Long id) {
        log.info("Invoke getPhoneTransferById method with id: {}", id);
        try {
            return transferService.getPhoneTransferById(id);
        } catch (FeignException e) {
            log.error("getPhoneTransferById method with id: {} throw FeignException with cause: {}, message: {}",
                    id, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PhoneTransferDto> getPhoneTransfersByCardNumberAndAccountDetailsId(Long phoneNumber,
                                                                                   Long accountDetailsId) {
        log.info("Invoke getPhoneTransfersByCardNumberAndAccountDetailsId method " +
                "with accountNumber: {} and accountDetailsId: {}", phoneNumber, accountDetailsId);
        List<PhoneTransferDto> transfers = List.of();
        try {
            transfers = transferService.getPhoneTransferByPhoneNumberAndAccountDetailsId(phoneNumber,
                    accountDetailsId);
        } catch (FeignException.NotFound ignore) {
            log.info("PhoneTransfers not found with accountId: {} and accountNumber: {}",
                    phoneNumber, accountDetailsId);
        } catch (FeignException e) {
            log.error("getPhoneTransfersByAccountNumberAndAccountDetailsId method with accountNumber: {} " +
                            "and accountDetailsId: {} throw FeignException with cause: {}, message: {}",
                    phoneNumber, accountDetailsId, e.getCause(), e.getMessage());
            throw new RuntimeException(e);
        }
        return transfers;
    }
}
