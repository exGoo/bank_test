package com.bank.history.controller;

import com.bank.history.dto.AuditDto;
import com.bank.history.service.AuditService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Аудит", description = "API для работы с таблицей аудита")
@Slf4j
@RequiredArgsConstructor
@RestController
@Timed
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService service;

    @Operation(
            summary = "Получение списка аудита",
            description = "Возвращает список записей аудита с поддержкой пагинации. "
                    + "Параметры пагинации (page, size, sort) могут быть переданы в запросе.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Успешное получение списка аудита",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(
                    responseCode = "400", description = "Некорректные параметры пагинации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(
                    responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping
    public ResponseEntity<Page<AuditDto>> getAllAudits(
            @Parameter(
                    description = "Параметры пагинации (page, size, sort). ",
                    example = "page=0&size=10&sort=createdAt,desc"
            ) Pageable pageable) {
        Page<AuditDto> result = service.getAllAudits(pageable);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
