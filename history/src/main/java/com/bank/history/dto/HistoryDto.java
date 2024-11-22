package com.bank.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Объект истории, содержащий информацию о различных аудиторских данных")
public class HistoryDto {

    @Schema(description = "Идентификатор истории", example = "")
    private Long id;

    @Schema(description = "Идентификатор аудита перевода", example = "")
    private Long transferAuditId;

    @Schema(description = "Идентификатор аудита профиля", example = "")
    private Long profileAuditId;

    @Schema(description = "Идентификатор аудита счета", example = "")
    private Long accountAuditId;

    @Schema(description = "Идентификатор аудита системы антифрода", example = "")
    private Long antiFraudAuditId;

    @Schema(description = "Идентификатор аудита публичной банковской информации", example = "")
    private Long publicBankInfoAuditId;

    @Schema(description = "Идентификатор аудита авторизации", example = "")
    private Long authorizationAuditId;
}
