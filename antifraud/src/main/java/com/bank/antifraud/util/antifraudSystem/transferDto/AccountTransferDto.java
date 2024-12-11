package com.bank.antifraud.util.antifraudSystem.transferDto;

import lombok.Data;

@Data
public class AccountTransferDto {

    Long id;

    Long accountNumber;

    Integer amount;

    String purpose;

    Long accountDetailsId;

}
