package com.bank.publicinfo.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDto {
    private Long id;
    private Long bik;
    private Long inn;
    private Long kpp;
    private Integer corAccount;
    private String city;
    private String jointStockCompany;
    private String name;
    private Set<Long> licenseIds;
    private Set<Long> certificateIds;
}
