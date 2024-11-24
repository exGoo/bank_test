package com.bank.antifraud.dto;

import com.bank.antifraud.model.SuspiciousCardTransfer;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * DTO for {@link SuspiciousCardTransfer}
 */
@Value
public class SuspiciousCardTransferDto {

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
