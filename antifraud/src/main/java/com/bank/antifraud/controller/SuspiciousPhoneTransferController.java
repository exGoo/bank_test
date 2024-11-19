package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/spt")
@RequiredArgsConstructor
public class SuspiciousPhoneTransferController {

    private final SuspiciousAccountTransfersService satService;
    private final SuspiciousAccountTransfersMapper satMapper;

    @GetMapping
    public ResponseEntity<List<SuspiciousAccountTransfersDto>> getAll() {
        List<SuspiciousAccountTransfersDto> satDtoList = satMapper.toDtoList(satService.getAll());
        return ResponseEntity.ok(satDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousAccountTransfersDto> getById(@PathVariable Long id) {
        SuspiciousAccountTransfersDto satDto = satMapper.toDto(satService.get(id));
        return ResponseEntity.ok(satDto);
    }

    @PostMapping
    public ResponseEntity<SuspiciousAccountTransfersDto> save(@RequestBody SuspiciousAccountTransfersDto satDto) {
        SuspiciousAccountTransfers sat = satMapper.toEntity(satDto);
        satService.add(sat);
    return ResponseEntity.ok(satMapper.toDto(sat));
    }

    @PatchMapping
    public ResponseEntity<SuspiciousAccountTransfersDto> update(@RequestBody SuspiciousAccountTransfersDto satDto) {
        SuspiciousAccountTransfers sat = satMapper.toEntity(satDto);
        satService.update(sat);
        return ResponseEntity.ok(satMapper.toDto(sat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        satService.remove(id);
        return new ResponseEntity<>("Deleted suspicious account transfer with " + id + " successfully",
                HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Incorrect body of request", HttpStatus.BAD_REQUEST);
    }

    //TODO: Fix controller SPT

}
