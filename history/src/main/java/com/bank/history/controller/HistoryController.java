package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.model.History;
import com.bank.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8088/api/history/swagger-ui/index.html
@Tag(name = "История", description = "API для работы с историей операций всех микросервисов")
@RequiredArgsConstructor
@RestController
public class HistoryController {

    private final HistoryService service;

    @Operation(
            summary = "Получение списка историй",
            description = "Возвращает список всех историй из всех микросервисов")
    @ApiResponse(
            responseCode = "200", description = "Успешное получение списка историй",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public ResponseEntity<List<HistoryDto>> getAllHistories() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllHistories());
    }

    @Operation(
            summary = "Получение истории по id",
            description = "Возвращает историю по её id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "История успешно найдена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(
                    responseCode = "404", description = "История не найдена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDto> getHistoryById(@Parameter(description = "id истории")
                                                     @PathVariable Long id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getHistoryById(id));
    }

    @Operation(
            summary = "Создание новой истории",
            description = "Создает новую историю на основе переданных данных")
    @ApiResponse(
            responseCode = "201", description = "История успешно создана",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping
    public ResponseEntity<History> createHistory(@Parameter(description = "Данные для создания истории")
                                                 @RequestBody HistoryDto historyDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.createHistory(historyDto));
    }

    @Operation(
            summary = "Обновление существующей истории",
            description = "Обновляет существующую истории по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "История успешно обновлена"),
            @ApiResponse(
                    responseCode = "404", description = "История не найдена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHistory(@Parameter(description = "id для обновления")
                                              @PathVariable Long id,
                                              @Parameter(description = "Новые данные истории")
                                              @RequestBody HistoryDto historyDto) {
        service.updateHistory(id, historyDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Operation(
            summary = "Частичное обновление существующей истории",
            description = "Частично обновляет существующую истории по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "История успешно обновлена"),
            @ApiResponse(
                    responseCode = "404", description = "История не найдена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> editHistory(@Parameter(description = "id для обновления")
                                            @PathVariable Long id,
                                            @Parameter(description = "Новые данные истории")
                                            @RequestBody HistoryDto historyDto) {
        service.editHistory(id, historyDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Operation(
            summary = "Удаление истории",
            description = "Удаляет историю по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "История успешно удалена"),
            @ApiResponse(responseCode = "404", description = "История не найдена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@Parameter(description = "id для удаления")
                                              @PathVariable Long id) {
        service.deleteHistory(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Hidden
    @ExceptionHandler(HistoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> handlerException(HistoryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
