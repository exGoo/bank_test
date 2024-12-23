package com.bank.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditDTO {

    private Long id;

    @NotNull
    private String entityType;

    @NotNull
    private String operationType;

    @NotNull
    private String createdBy;

    private String modifiedBy;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String newEntityJson;

    @NotNull
    private String entityJson;

    public AuditDTO(String entityType, String operationType, String createdBy, String modifiedBy,
                    LocalDateTime createdAt, LocalDateTime modifiedAt, String newEntityJson, String entityJson) {
        this.entityType = entityType;
        this.operationType = operationType;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.newEntityJson = newEntityJson;
        this.entityJson = entityJson;
    }
}
