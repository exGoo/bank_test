package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhoneTransferDto {

    Long id;

    Long phoneNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;
}
