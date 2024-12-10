package com.bank.publicinfo.dto;

import com.bank.publicinfo.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankDetailsDto implements Auditable<Long> {

    @NotNull
    private Long id;

    @NotNull
    private Long bik;

    @NotNull
    private Long inn;

    @NotNull
    private Long kpp;

    @NotNull
    private Integer corAccount;

    @NotNull
    private String city;

    @NotNull
    private String jointStockCompany;

    @NotNull
    private String name;

    private Set<Long> licenseIds;

    private Set<Long> certificateIds;

    @JsonIgnore
    @Override
    public Long getEntityId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getEntityName() {
        return "BankDetails";
    }
}
