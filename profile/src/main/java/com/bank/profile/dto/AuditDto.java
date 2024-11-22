package com.bank.profile.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
@Data
public class AuditDto {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
