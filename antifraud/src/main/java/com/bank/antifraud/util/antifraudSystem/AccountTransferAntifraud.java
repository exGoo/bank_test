package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import java.util.List;

public interface AccountTransferAntifraud extends TransferChecker {

    SuspiciousAccountTransfers checkTransfer(SuspiciousAccountTransfers sat);

    AccountTransferDto getTransferById(Long id);

    List<AccountTransferDto> getTransfersByAccountNumberAndAccountDetailsId(Long accountNumber,
                                                                            Long accountDetailsId);
}
