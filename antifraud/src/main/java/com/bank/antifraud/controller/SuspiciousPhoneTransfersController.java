package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/spt")
@RequiredArgsConstructor
@Tag(name = "Suspicious Phone Transfers", description = "API для управления подозрительными переводами на телефон")
public class SuspiciousPhoneTransfersController {

    private final SuspiciousPhoneTransfersService sptService;

    @Operation(summary = "Получить все подозрительные переводы на телефон")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<SuspiciousPhoneTransfersDto>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("invoke method getAll");
        return ResponseEntity.ok(sptService.getAll(PageRequest.of(page, size)));
    }

    @Operation(summary = "Получить подозрительный перевод на телефон по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuspiciousPhoneTransfersDto.class))),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransfersDto> get(@PathVariable("id") Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(sptService.get(id));
    }

    @Operation(summary = "Сохранить новый подозрительный перевод на телефон")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<SuspiciousPhoneTransfersDto> add(@RequestBody SuspiciousPhoneTransfersDto sptDto) {
        log.info("invoke method save with arg({})", sptDto);
        final SuspiciousPhoneTransfersDto saved = sptService.add(sptDto);
        log.info("invoked method save success");
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Обновить подозрительный перевод на телефон по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SuspiciousPhoneTransfersDto sptDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, sptDto);
        sptService.update(id, sptDto);
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious phone transfer with id " + id + " was updated.");
    }

    @Operation(summary = "Удалить подозрительный перевод на телефон по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Перевод не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        log.info("invoke method remove with id {}", id);
        sptService.remove(id);
        log.info("invoked method remove success");
        return ResponseEntity.ok("Delete success. Suspicious phone transfer with id " + id + " was deleted.");
    }
}
