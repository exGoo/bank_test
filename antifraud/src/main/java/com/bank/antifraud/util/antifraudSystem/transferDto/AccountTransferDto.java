package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTransferDto {

    Long id;

    Long accountNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;

}
