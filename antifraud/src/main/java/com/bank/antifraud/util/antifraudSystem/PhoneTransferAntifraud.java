package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;

import java.util.List;

public interface PhoneTransferAntifraud extends TransferChecker {

    SuspiciousPhoneTransfers checkPhoneTransfer(SuspiciousPhoneTransfers spt);

    PhoneTransferDto getPhoneTransferById(Long id);

    List<PhoneTransferDto> getPhoneTransfersByCardNumberAndAccountDetailsId(Long phoneNumber,
                                                                            Long accountDetailsId);
}
