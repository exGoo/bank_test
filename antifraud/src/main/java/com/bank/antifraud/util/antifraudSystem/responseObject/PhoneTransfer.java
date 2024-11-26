package com.bank.antifraud.util.antifraudSystem.responseObject;

import lombok.Data;

@Data
public class PhoneTransfer {

    Long id;

    Long phoneNumber;

    Integer amount;

    String purpose;

    Long accountDetailsId;

}
