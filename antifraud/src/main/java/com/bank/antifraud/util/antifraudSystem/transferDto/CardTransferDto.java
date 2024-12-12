package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardTransferDto {

    Long id;

    Long cardNumber;

    BigDecimal amount;

    String purpose;

    Long accountDetailsId;
}
