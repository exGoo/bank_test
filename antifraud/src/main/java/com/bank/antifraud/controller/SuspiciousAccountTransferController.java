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
        return ResponseEntity.ok(satMapper.toDtoList(satService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousAccountTransfersDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(satMapper.toDto(satService.get(id)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody SuspiciousAccountTransfersDto satDto) {
        satService.add(satMapper.toEntity(satDto));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                             @RequestBody SuspiciousAccountTransfersDto satDto) {
        satService.update(id, satMapper.update(satDto));
        return new ResponseEntity<>("Update suspicious account transfer with " + id + " successfully",
                HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        satService.remove(id);
        return new ResponseEntity<>("Delete suspicious account transfer with " + id + " successfully",
                HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Incorrect body of request", HttpStatus.BAD_REQUEST);
    }

}
