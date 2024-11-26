package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/sct")
@RequiredArgsConstructor
@Tag(name = "Suspicious Card Transfers", description = "API для управления подозрительными переводами по картам")
public class SuspiciousCardTransferController {

    private final SuspiciousCardTransferService sctService;
    private final SuspiciousCardTransferMapper sctMapper;

    @Operation(summary = "Получить все подозрительные переводы по картам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<SuspiciousCardTransferDto>> getAll() {
        log.info("invoke method getAll");
        return ResponseEntity.ok(sctMapper.toDtoList(sctService.getAll()));
    }

    @Operation(summary = "Получить подозрительный перевод на карту по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousCardTransferDto.class))),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousCardTransferDto> get(@PathVariable("id") Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(sctMapper.toDto(sctService.get(id)));
    }

    @Operation(summary = "Сохранить новый подозрительный перевод на карту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное создание"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody SuspiciousCardTransferDto sctDto) {
        log.info("invoke method save with arg({})", sctDto);
        sctService.add(sctMapper.toEntity(sctDto));
        log.info("invoked method save success");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Обновить подозрительный перевод на карту по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SuspiciousCardTransferDto sctDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, sctDto);
        sctService.update(id, sctMapper.update(sctDto));
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious card transfer with id " + id + " was updated.");
    }

    @Operation(summary = "Удалить подозрительный перевод на карту по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        log.info("invoke method remove with id {}", id);
        sctService.remove(id);
        log.info("invoked method remove success");
        return ResponseEntity.ok("Delete success. Suspicious card transfer with id " + id + " was deleted.");
    }

}
