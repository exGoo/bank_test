package com.bank.antifraud.dto;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * DTO for {@link SuspiciousAccountTransfers}
 */
@Value
public class SuspiciousAccountTransfersDto {

    @NotNull
    Long accountTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

    @NotNull
    String suspiciousReason;

}
