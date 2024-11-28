package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;
import com.bank.history.service.HistoryService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//http://localhost:8088/api/history/swagger-ui/index.html
@Tag(name = "История", description = "API для работы с историей операций всех микросервисов")
@Slf4j
@RequiredArgsConstructor
@Timed
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
        log.info("Вызов метода: getAllHistories");
        List<HistoryDto> result = service.getAllHistories();
        log.info("Метод getAllHistories успешно выполнен. Результат: {}", result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
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
        log.info("Вызов метода: getHistoryById с id: {}", id);
        HistoryDto result = service.getHistoryById(id);
        log.info("Метод getHistoryById успешно выполнен. Результат: {}", result);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
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
        log.info("Вызов метода: createHistory с данными: {}", historyDto);
        History result = service.createHistory(historyDto);
        log.info("Метод createHistory успешно выполнен. Результат: {}", result);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
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
        log.info("Вызов метода: updateHistory с id: {} и данными: {}", id, historyDto);
        service.updateHistory(id, historyDto);
        log.info("Метод updateHistory успешно выполнен.");
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
        log.info("Вызов метода: editHistory с id: {} и данными: {}", id, historyDto);
        service.editHistory(id, historyDto);
        log.info("Метод editHistory успешно выполнен.");
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
        log.info("Вызов метода: deleteHistory с id: {}", id);
        service.deleteHistory(id);
        log.info("Метод deleteHistory успешно выполнен.");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
