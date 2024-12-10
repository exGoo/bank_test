package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/licenses")
@Tag(name = "Licenses", description = "API для управления лицензиями банка")
public class LicenseController {

    private LicenseService licenseService;

    @Autowired
    public void setLicenseService(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @Operation(summary = "Получить лицензию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение лицензии",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LicenseDto.class))),
            @ApiResponse(responseCode = "404", description = "Лицензия не найдена")})
    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getLicenseById(@PathVariable Long id) {
        LicenseDto licenseDto = licenseService.findById(id);
        return new ResponseEntity<>(licenseDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все лицензии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка лицензий",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LicenseDto.class)))})
    @GetMapping
    public ResponseEntity<List<LicenseDto>> getAllLicenses() {
        List<LicenseDto> licenseDto = licenseService.findAll();
        return new ResponseEntity<>(licenseDto, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую лицензию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное добавление"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<LicenseDto> addLicense(@RequestBody LicenseDto licenseDto) {
        LicenseDto createdLicense = licenseService.addLicence(licenseDto);
        return new ResponseEntity<>(createdLicense, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить лицензию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Лицензия не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LicenseDto> updateLicense(@PathVariable Long id, @RequestBody LicenseDto licenseDto) {
        LicenseDto updateLicense = licenseService.updateLicense(id, licenseDto);
        return new ResponseEntity<>(updateLicense, HttpStatus.OK);
    }

    @Operation(summary = "Удалить лицензию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Лицензия не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        licenseService.deleteLicenceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
