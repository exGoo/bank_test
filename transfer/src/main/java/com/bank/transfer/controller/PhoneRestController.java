package com.bank.transfer.controller;

import com.bank.transfer.aspects.AuditAspect;
import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.service.PhoneTransferService;
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

@RestController
@RequestMapping("/phone")
public class PhoneRestController {
    private final PhoneTransferService phoneTransferService;
    private final AuditAspect auditAspect;

    @Autowired
    public PhoneRestController(PhoneTransferService phoneTransferService, AuditAspect auditAspect) {
        this.phoneTransferService = phoneTransferService;
        this.auditAspect = auditAspect;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<PhoneTransferDTO>> getPhoneTransferById(@PathVariable Long id) {
        return new ResponseEntity<>(phoneTransferService.getPhoneTransferById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PhoneTransferDTO>> getPhoneTransfer() {
        final List<PhoneTransferDTO> phoneTransfersDTO = phoneTransferService.allPhoneTransfer();

        if (phoneTransfersDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(phoneTransfersDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addPhoneTransfer(@RequestBody PhoneTransferDTO phoneTransferDTO) {
        phoneTransferService.savePhoneTransfer(phoneTransferDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping("/{id}")
    public PhoneTransfer updatePhoneTransfer(@RequestBody PhoneTransferDTO phoneTransferDTO,
                                                             @PathVariable("id") long id) {
        final PhoneTransfer phoneTransferUpdate = phoneTransferService.updatePhoneTransferById(phoneTransferDTO, id);
        return phoneTransferUpdate;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoneTransfer(@PathVariable long id) {
        phoneTransferService.deletePhoneTransfer(id);
        return ResponseEntity.noContent().build();
    }
}
