package com.bank.profile.dto;

import com.bank.profile.dto.util.Identifiable;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@Builder
public class AuditDto implements Identifiable {

    private Long id;
    @Size(max = 40, message = "до 40")
    private String entityType;

    @Size(max = 255, message = "до 255")
    private String operationType;

    @Size(max = 255, message = "до 255")
    private String createdBy;

    @Size(max = 255, message = "до 255")
    private String modifiedBy;

    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private String newEntityJson;
    private String entityJson;
}
