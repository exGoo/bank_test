package com.bank.profile.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.ZonedDateTime;
@Data
public class AuditDto {
    private Long id;
    private String entityType;
    private String operationType;
    private String createdBy;
    private String modifiedBy;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
