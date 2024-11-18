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

    @NotNull(message = "The entity type must not be null")
    @Size(max = Audit.MAX_LENGTH_FOR_ENTITY_TYPE, message = "Max length 40 characters")
    String entityType;

    @NotNull(message = "The operation type must not be null")
    String operationType;

    @NotNull(message = "The creator must be listed")
    String createdBy;

    String modifiedBy;

    @NotNull(message = "The created date must not be null")
    OffsetDateTime createdAt;

    OffsetDateTime modifiedAt;

    String newEntityJson;

    @NotNull(message = "The entity json must not be null")
    String entityJson;

}
