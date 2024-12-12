package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Service
@FeignClient(name = "transfer-app")
public interface TransferServiceClient {

    @GetMapping("/api/transfer/accountTransfer/{id}")
    AccountTransferDto getAccountTransferById(@PathVariable Long id);

    @GetMapping("/api/transfer/accountTransfer")
    List<AccountTransferDto> getAccountTransferByAccountNumberAndAccountDetailsId(
            @RequestParam Long accountNumber,
            @RequestParam Long accountDetailsId
    );

    @GetMapping("/api/transfer/cardTransfer/{id}")
    CardTransferDto getCardTransferById(@PathVariable Long id);

    @GetMapping("/api/transfer/cardTransfer")
    List<CardTransferDto> getCardTransferByCardNumberAndAccountDetailsId(
            @RequestParam Long cardNumber,
            @RequestParam Long accountDetailsId
    );

    @GetMapping("api/transfer/phoneTransfer/{id}")
    PhoneTransferDto getPhoneTransferById(@PathVariable Long id);

    @GetMapping("/api/transfer/phoneTransfer")
    List<PhoneTransferDto> getPhoneTransferByPhoneNumberAndAccountDetailsId(
            @RequestParam Long phoneNumber,
            @RequestParam Long accountDetailsId
    );
}
