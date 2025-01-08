package com.bank.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditDto {
    private Long id;

    private String entityType;

    private String operationType;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String newEntityJson;

    private String entityJson;
}
