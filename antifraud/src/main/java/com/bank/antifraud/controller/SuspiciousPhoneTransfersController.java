package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.mapper.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spt")
@RequiredArgsConstructor
public class SuspiciousPhoneTransfersController {

    private final SuspiciousPhoneTransfersService sptService;
    private final SuspiciousPhoneTransfersMapper sptMapper;

    @GetMapping
    public ResponseEntity<List<SuspiciousPhoneTransfersDto>> getAll() {
        log.info("invoke method getAll");
        return ResponseEntity.ok(sptMapper.toDtoList(sptService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousPhoneTransfersDto> get(@PathVariable("id") Long id) {
        log.info("invoke method get with id {}", id);
        return ResponseEntity.ok(sptMapper.toDto(sptService.get(id)));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody SuspiciousPhoneTransfersDto sptDto) {
        log.info("invoke method save with arg({})", sptDto);
        sptService.add(sptMapper.toEntity(sptDto));
        log.info("invoked method save success");
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SuspiciousPhoneTransfersDto sptDto) {
        log.info("invoke method update with args(id: {}, dto: {})", id, sptDto);
        sptService.update(id, sptMapper.update(sptDto));
        log.info("invoked method update success");
        return ResponseEntity.ok("Update success. Suspicious account transfer with id " + id + " was updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        log.info("invoke method remove with id {}", id);
        sptService.remove(id);
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

