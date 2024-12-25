package com.bank.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Объект истории, содержащий информацию о различных аудиторских данных")
public class HistoryDto {

    @Schema(description = "Идентификатор истории")
    private Long id;

    @Schema(description = "Идентификатор аудита перевода")
    private Long transferAuditId;

    @Schema(description = "Идентификатор аудита профиля")
    private Long profileAuditId;

    @Schema(description = "Идентификатор аудита счета")
    private Long accountAuditId;

    @Schema(description = "Идентификатор аудита системы антифрода")
    private Long antiFraudAuditId;

    @Schema(description = "Идентификатор аудита публичной банковской информации")
    private Long publicBankInfoAuditId;

    @Schema(description = "Идентификатор аудита авторизации")
    private Long authorizationAuditId;
}
