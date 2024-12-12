package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;

import java.util.List;

public interface CardTransferAntifraud extends TransferChecker {

    SuspiciousCardTransfer checkTransfer(SuspiciousCardTransfer sct);

    CardTransferDto getTransferById(Long id);

    List<CardTransferDto> getTransfersByCardNumberAndAccountDetailsId(Long cardNumber,
                                                                      Long accountDetailsId);
}
