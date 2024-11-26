package com.bank.antifraud.util.antifraudSystem.responseObject;

import lombok.Data;

@Data
public class CardTransfer {

    Long id;

    Long cardNumber;

    Integer amount;

    String purpose;

    Long accountDetailsId;

}
