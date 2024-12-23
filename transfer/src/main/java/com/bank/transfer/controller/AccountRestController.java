package com.bank.transfer.controller;


import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.service.AccountTransferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "main_methods")
@RestController
@RequestMapping("/account")

public class AccountRestController {
    private final AccountTransferService accountTransferService;
    private final AuditAspect auditAspect;

    @Autowired
    public AccountRestController(AccountTransferService accountTransferService, AuditAspect auditAspect) {
        this.accountTransferService = accountTransferService;
        this.auditAspect = auditAspect;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<AccountTransferDTO>> getAccountTransferById(@PathVariable Long id) {
        return new ResponseEntity<>(accountTransferService.getAccountTransferById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountTransferDTO>> getAccountTransfer() {
        final List<AccountTransferDTO> accountTransfersDTO = accountTransferService.allAccountTransfer();

        if (accountTransfersDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(accountTransfersDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addAccountTransfer(@RequestBody AccountTransferDTO accountTransferDTO) {
        accountTransferService.saveAccountTransfer(accountTransferDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public AccountTransfer updateAccountTransfer(@RequestBody AccountTransferDTO accountTransferDTO,
                                                                 @PathVariable("id") long id) {
        final AccountTransfer accountTransferUpdate =
                accountTransferService.updateAccountTransferById(accountTransferDTO, id);
        return accountTransferUpdate;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountTransfer(@PathVariable long id) {
        accountTransferService.deleteAccountTransfer(id);
        return ResponseEntity.noContent().build();
    }
}
