package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
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
@RequestMapping("/sat")
@RequiredArgsConstructor
public class SuspiciousAccountTransferController {

    private final SuspiciousAccountTransfersService satService;
    private final SuspiciousAccountTransfersMapper satMapper;

    @GetMapping
    public ResponseEntity<List<SuspiciousAccountTransfersDto>> getAll() {
        log.info("invoke method getAll");
        return ResponseEntity.ok(satMapper.toDtoList(satService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousAccountTransfersDto> get(@PathVariable Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(satMapper.toDto(satService.get(id)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody SuspiciousAccountTransfersDto satDto) {
        log.info("invoke method save with arg({})", satDto);
        satService.add(satMapper.toEntity(satDto));
        log.info("invoked method save success");
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                             @RequestBody SuspiciousAccountTransfersDto satDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, satDto);
        satService.update(id, satMapper.update(satDto));
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious account transfer with id " + id + " was updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("invoke method delete with id {}", id);
        satService.remove(id);
        log.info("invoked method delete success");
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
