package com.bank.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardTransferDTO {

    private Long id;

    @NotNull(message = "Card number cannot be null")
    private Long cardNumber;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String purpose;

    @NotNull(message = "Card details ID cannot be null")
    private Long accountDetailsId;

    public CardTransferDTO(Long cardNumber, BigDecimal amount, String purpose, Long accountDetailsId) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.purpose = purpose;
        this.accountDetailsId = accountDetailsId;
    }
}

