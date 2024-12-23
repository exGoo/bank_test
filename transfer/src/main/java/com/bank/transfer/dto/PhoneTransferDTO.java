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
public class PhoneTransferDTO {

    private Long id; // Если необходимо возвращать id

    @NotNull(message = "Phone number cannot be null")
    private Long phoneNumber;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String purpose;

    @NotNull(message = "Account details ID cannot be null")
    private Long accountDetailsId;

    public PhoneTransferDTO(Long phoneNumber, BigDecimal amount, String purpose, Long accountDetailsId) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.purpose = purpose;
        this.accountDetailsId = accountDetailsId;
    }
}
