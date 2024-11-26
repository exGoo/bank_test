package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
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
@RequestMapping("/sat")
@RequiredArgsConstructor
@Tag(name = "Suspicious Account Transfers", description = "API для управления подозрительными переводами на аккаунты")
public class SuspiciousAccountTransferController {

    private final SuspiciousAccountTransfersService satService;
    private final SuspiciousAccountTransfersMapper satMapper;

    @Operation(summary = "Получить все подозрительные переводы на аккаунты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransfersDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<SuspiciousAccountTransfersDto>> getAll() {
        log.info("invoke method getAll");
        return ResponseEntity.ok(satMapper.toDtoList(satService.getAll()));
    }

    @Operation(summary = "Получить подозрительный перевод на аккаунт по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousAccountTransfersDto.class))),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousAccountTransfersDto> get(@PathVariable Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(satMapper.toDto(satService.get(id)));
    }

    @Operation(summary = "Сохранить новый подозрительный перевод на аккаунт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное создание"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody SuspiciousAccountTransfersDto satDto) {
        log.info("invoke method save with arg({})", satDto);
        satService.add(satMapper.toEntity(satDto));
        log.info("invoked method save success");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Обновить подозрительный перевод на аккаунт по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody SuspiciousAccountTransfersDto satDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, satDto);
        satService.update(id, satMapper.update(satDto));
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious account transfer with id " + id + " was updated.");
    }

    @Operation(summary = "Удалить подозрительный перевод на аккаунт по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("invoke method delete with id {}", id);
        satService.remove(id);
        log.info("invoked method delete success");
        return ResponseEntity.ok("Delete success. Suspicious account transfer with id " + id + " was deleted.");
    }

}
