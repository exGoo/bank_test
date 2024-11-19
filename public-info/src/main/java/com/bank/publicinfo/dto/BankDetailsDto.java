package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDto {
    private Long id;
    private Long bik;
    private Long inn;
    private Long kpp;
    private int corAccount;
}
