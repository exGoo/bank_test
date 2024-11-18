package com.bank.antifraud.dto;

import com.bank.antifraud.model.Audit;
import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * DTO for update {@link Audit}
 */

@Value
public class AuditUpdateDto {

    @NotNull
    Long id;

    @NotNull
    String modifiedBy;

    @NotNull
    String newEntityJson;

}
