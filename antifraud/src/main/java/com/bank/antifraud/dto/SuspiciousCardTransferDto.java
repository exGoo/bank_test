package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import lombok.Value;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link SuspiciousCardTransfer}
 */
@Value
public class SuspiciousCardTransferDto {

    Long id;

    @NotNull
    Long cardTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockerReason;

    @NotNull
    String suspiciousReason;
}
