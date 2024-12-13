package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
@RequestMapping("/certificates")
@Tag(name = "Certificates", description = "API для управления сертификатами банка")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @Operation(summary = "Получить сертификат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение сертификата",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CertificateDto.class))),
            @ApiResponse(responseCode = "404", description = "Сертификат не найдена")})
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable Long id) {
        CertificateDto certificateDto = certificateService.findById(id);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все сертификаты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка сертификатов",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CertificateDto.class)))})
    @GetMapping
    public ResponseEntity<List<CertificateDto>> getAllCertificates() {
        List<CertificateDto> certificateDto = certificateService.findAll();
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый сертификат")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное добавление"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto) {
        CertificateDto createdCertificate = certificateService.addCertificate(certificateDto);
        return new ResponseEntity<>(createdCertificate, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить сертификат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Сертификат не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CertificateDto> updateCertificate(@PathVariable Long id, @RequestBody CertificateDto certificateDto) {
        CertificateDto updatedCertificate = certificateService.updateCertificate(id, certificateDto);
        return new ResponseEntity<>(updatedCertificate, HttpStatus.OK);
    }

    @Operation(summary = "Удалить сертификат по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Сертификат не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
