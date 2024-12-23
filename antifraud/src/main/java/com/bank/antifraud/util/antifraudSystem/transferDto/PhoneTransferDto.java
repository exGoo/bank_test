package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PhoneTransferDto {

    Long id;

    Long phoneNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;
}
