package com.bank.history.controller;

import com.bank.history.dto.AuditDto;
import com.bank.history.service.AuditService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Аудит", description = "API для работы с таблицей аудита")
@Slf4j
@RequiredArgsConstructor
@RestController
@Timed
@RequestMapping("/audit")
public class AuditController {

    private final AuditService service;

    @Operation(summary = "Получение списка аудита")
    @ApiResponse(
            responseCode = "200", description = "Успешное получение списка аудита",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public ResponseEntity<List<AuditDto>> getAllAudits() {
        log.info("Вызов метода: getAllAudits");
        List<AuditDto> result = service.getAllAudits();
        log.info("Метод getAllAudits успешно выполнен. Результат: {}", result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
