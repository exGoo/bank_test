package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
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
import java.util.List;

@RestController
@RequestMapping("/bank-details")
@Tag(name = "Bank Details", description = "API для управления банковской информацией")
@RequiredArgsConstructor
public class BankDetailsController {

    private final BankDetailsService bankDetailsService;

    @Operation(summary = "Получить банковскую информацию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение информации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Информация не найдена")})
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDto> getBankDetailsById(@PathVariable Long id) {
        BankDetailsDto bankDetailsDto = bankDetailsService.findById(id);
        return new ResponseEntity<>(bankDetailsDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о всех банках")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение информации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankDetailsDto.class)))})
    @GetMapping
    public ResponseEntity<List<BankDetailsDto>> getAllBankDetails() {
        List<BankDetailsDto> bankDetailsDtos = bankDetailsService.findAllWithRelations();
        return new ResponseEntity<>(bankDetailsDtos, HttpStatus.OK);
    }

    @Operation(summary = "Получить банковскую информацию по банкам в определенном городе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение информации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankDetailsDto.class))),
            @ApiResponse(responseCode = "404", description = "Информация не найдена")})
    @GetMapping("/city/{city}")
    public ResponseEntity<List<BankDetailsDto>> getBankDetailsByCity(@PathVariable String city) {
        List<BankDetailsDto> bankDetailsDtos = bankDetailsService.findByCity(city);
        return new ResponseEntity<>(bankDetailsDtos, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую банковскую информацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное добавление"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<BankDetailsDto> addBankDetails(@RequestBody BankDetailsDto bankDetailsDto) {
        BankDetailsDto createdBankDetails = bankDetailsService.addBankDetails(bankDetailsDto);
        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить банковскую информацию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Информация не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BankDetailsDto> updateBankDetails(@PathVariable Long id, @RequestBody BankDetailsDto bankDetailsDto) {
        BankDetailsDto updatedBankDetails = bankDetailsService.updateBankDetails(id, bankDetailsDto);
        return new ResponseEntity<>(updatedBankDetails, HttpStatus.OK);
    }

    @Operation(summary = "Удалить банковскую иформацию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Информация не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        bankDetailsService.deleteBankDetailsById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
