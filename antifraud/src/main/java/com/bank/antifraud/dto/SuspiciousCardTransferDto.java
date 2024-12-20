package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link SuspiciousCardTransfer}
 */
@Value
@ToString
@Builder
public class SuspiciousCardTransferDto {

    Long id;

    @NotNull
    Long cardTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

    @NotNull
    String suspiciousReason;
}
