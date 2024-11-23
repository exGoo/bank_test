package com.bank.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class AccountDto {
    private Long id;

    @NotNull(message = "Поле 'passportId' не должно быть равно null")
    private Long passportId;

    @NotNull(message = "Поле 'accountNumber' не должно быть равно null")
    private Long accountNumber;

    @NotNull(message = "Поле 'bankDetailsId' не должно быть равно null")
    private Long bankDetailsId;

    @NotNull(message = "Поле 'Money' не должно быть равно null")
    private BigDecimal money;

    @NotNull(message = "Поле 'negativeBalance' не должно быть равно null")
    private Boolean negativeBalance;

    @NotNull(message = "Поле 'profileId' не должно быть равно null")
    private Long profileId;
}
