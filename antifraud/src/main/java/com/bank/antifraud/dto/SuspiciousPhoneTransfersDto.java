package com.bank.antifraud.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * DTO for {@link com.bank.antifraud.model.SuspiciousPhoneTransfers}
 */
@Value
public class SuspiciousPhoneTransfersDto {

    @NotNull
    Long phoneTransferId;

    @NotNull
    Boolean isBlocked;

    @NotNull
    Boolean isSuspicious;

    String blockedReason;

    @NotNull
    String suspiciousReason;

}
