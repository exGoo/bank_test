package com.bank.antifraud.dto;

import com.bank.antifraud.model.Audit;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

/**
 * DTO for {@link Audit}
 */
@Value
public class AuditDto {

    Long id;

    @NotNull(message = "The field must not be empty")
    @Size(max = Audit.MAX_LENGTH_FOR_ENTITY_TYPE, message = "Max length 40 characters")
    String entityType;

    @NotNull(message = "The field must not be empty")
    String operationType;

    @NotNull(message = "The field must not be empty")
    String createdBy;

    String modifiedBy;

    @NotNull(message = "The field must not be empty")
    OffsetDateTime createdAt;

    OffsetDateTime modifiedAt;

    String newEntityJson;

    @NotNull(message = "The field must not be empty")
    String entityJson;

}
