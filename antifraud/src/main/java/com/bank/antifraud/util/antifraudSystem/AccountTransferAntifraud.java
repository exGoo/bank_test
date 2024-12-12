package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import java.util.List;

public interface AccountTransferAntifraud extends TransferChecker {

    SuspiciousAccountTransfers checkAccountTransfer(SuspiciousAccountTransfers sat);

    AccountTransferDto getAccountTransferById(Long id);

    List<AccountTransferDto> getAccountTransfersByAccountNumberAndAccountDetailsId(Long accountNumber,
                                                                                   Long accountDetailsId);
}
