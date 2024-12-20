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

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenseDto implements Auditable<Long> {

    @NotNull
    private Long id;

    private byte[] photo;

    @NotNull
    private Long bankDetailsId;

    public LicenseDto(Long id, Long bankDetailsId) {
        this.id = id;
        this.bankDetailsId = bankDetailsId;
    }

    @JsonIgnore
    @Override
    public Long getEntityId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getEntityName() {
        return "License";
    }
}
