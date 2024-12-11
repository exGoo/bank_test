package com.bank.antifraud.util.antifraudSystem;

import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "transfer-app")
public interface TransferServiceClient {

    @GetMapping("/api/transfer/accountTransfer/{id}")
    Optional<AccountTransferDto> getAccountTransfer(@PathVariable Long id);

    @GetMapping("/api/transfer/accountTransfer")
    List<AccountTransferDto> getAccountTransferByAccountNumberAndAccountDetailsId(
            @RequestParam Long accountNumber,
            @RequestParam Long accountDetailsId
    );

    @GetMapping("/api/transfer/cardTransfer/{id}")
    Optional<CardTransferDto> getCardTransfer(@PathVariable Long id);

    @GetMapping("/api/transfer/cardTransfer")
    List<CardTransferDto> getCardTransferByCardNumberAndAccountDetailsId(
            @RequestParam Long cardNumber,
            @RequestParam Long accountDetailsId
    );

    @GetMapping("api/transfer/phoneTransfer/{id}")
    Optional<PhoneTransferDto> getPhoneTransfer(@PathVariable Long id);

    @GetMapping("/api/transfer/phoneTransfer")
    List<PhoneTransferDto> getPhoneTransferByPhoneNumberAndAccountDetailsId(
            @RequestParam String phoneNumber,
            @RequestParam Long accountDetailsId
    );
}
