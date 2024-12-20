package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.service.ATMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/atms")
@Tag(name = "ATMs", description = "API для управления банкоматами")
@RequiredArgsConstructor
public class ATMController {

    private final ATMService atmService;

    @Operation(summary = "Получить банкомат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение банкомата",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ATMDto.class))),
            @ApiResponse(responseCode = "404", description = "Банкомат не найден")})
    @GetMapping("/{id}")
    public ResponseEntity<ATMDto> getATMById(@PathVariable Long id) {
        ATMDto atmDto = atmService.findById(id);
        return new ResponseEntity<>(atmDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все банкоматы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка банкоматов",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ATMDto.class)))})
    @GetMapping
    public ResponseEntity<List<ATMDto>> getAllAtms() {
        List<ATMDto> atmDto = atmService.findAll();
        return new ResponseEntity<>(atmDto, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый банкомат")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное добавление"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<ATMDto> addATM(@Valid @RequestBody ATMDto atmDto) {
        ATMDto createdAtm = atmService.addATM(atmDto);
        return new ResponseEntity<>(createdAtm, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить банкомат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Банкомат не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ATMDto> updateATM(@PathVariable Long id, @RequestBody ATMDto atmDto) {
        ATMDto updatedATM = atmService.updateATM(id, atmDto);
        return new ResponseEntity<>(updatedATM, HttpStatus.OK);
    }

    @Operation(summary = "Удалить банкомат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Банкомат не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteATM(@PathVariable Long id) {
        atmService.deleteATMById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
