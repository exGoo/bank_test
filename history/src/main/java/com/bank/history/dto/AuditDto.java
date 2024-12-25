package com.bank.history.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditDto {
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
}
