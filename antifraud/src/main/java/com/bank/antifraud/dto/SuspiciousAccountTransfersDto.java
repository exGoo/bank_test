package com.bank.antifraud.dto;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link SuspiciousAccountTransfers}
 */
@Value
@ToString
@Builder
public class SuspiciousAccountTransfersDto {

    Long id;

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
