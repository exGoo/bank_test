package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;

import java.util.List;

public interface CardTransferAntifraud extends TransferChecker {

    SuspiciousCardTransfer checkCardTransfer(SuspiciousCardTransfer sct);

    CardTransferDto getCardTransferById(Long id);

    List<CardTransferDto> getCardTransfersByCardNumberAndAccountDetailsId(Long cardNumber,
                                                                      Long accountDetailsId);
}
