package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/sct")
@RequiredArgsConstructor
public class SuspiciousCardTransferController {

    private final SuspiciousCardTransferService sctService;
    private final SuspiciousCardTransferMapper sctMapper;

    @GetMapping
    public ResponseEntity<List<SuspiciousCardTransferDto>> getAll() {
        log.info("invoke method getAll");
        return ResponseEntity.ok(sctMapper.toDtoList(sctService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousCardTransferDto> get(@PathVariable("id") Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(sctMapper.toDto(sctService.get(id)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody SuspiciousCardTransferDto sctDto) {
        log.info("invoke method save with arg({})", sctDto);
        sctService.add(sctMapper.toEntity(sctDto));
        log.info("invoked method save success");
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SuspiciousCardTransferDto sctDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, sctDto);
        sctService.update(id, sctMapper.update(sctDto));
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious account transfer with id " + id + " was updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        log.info("invoke method remove with id {}", id);
        sctService.remove(id);
        log.info("invoked method remove success");
        return ResponseEntity.ok("Delete success. Suspicious account transfer with id " + id + " was deleted.");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Incorrect body of request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public void handleLogException(Exception e) {
        log.error("cause: {}, message: {}", e.getCause(), e.getMessage());
    }

}

