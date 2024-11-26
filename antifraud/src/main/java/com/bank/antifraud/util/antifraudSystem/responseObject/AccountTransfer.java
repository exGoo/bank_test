package com.bank.antifraud.util.antifraudSystem.responseObject;

import lombok.Data;

@Data
public class AccountTransfer {

    Long id;

    Long accountNumber;

    Integer amount;

    String purpose;

    Long accountDetailsId;

}
