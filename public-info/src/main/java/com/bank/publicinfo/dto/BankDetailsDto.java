package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String city;
    private String jointStockCompany;
    private String name;
    private List<LicenseDto> licenses;
    private List<CertificateDto> certificates;
}
