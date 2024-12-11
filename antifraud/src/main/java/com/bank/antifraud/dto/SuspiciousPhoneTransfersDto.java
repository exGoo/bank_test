package com.bank.antifraud.dto;

import lombok.Value;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link com.bank.antifraud.entity.SuspiciousPhoneTransfers}
 */
@Value
public class SuspiciousPhoneTransfersDto {

    Long id;

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
