package com.bank.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransferDTO {

    private Long id;

    @NotNull(message = "Account number cannot be null")
    private Long accountNumber;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String purpose;

    @NotNull(message = "Account details ID cannot be null")
    private Long accountDetailsId;

    public AccountTransferDTO(Long accountNumber, BigDecimal amount, String purpose, Long accountDetailsId) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.purpose = purpose;
        this.accountDetailsId = accountDetailsId;
    }
}
