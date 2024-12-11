package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Data;

@Data
public class CardTransferDto {

    Long id;

    Long cardNumber;

    Integer amount;

    String purpose;

    Long accountDetailsId;
}
