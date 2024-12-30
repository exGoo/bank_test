package com.bank.transfer.controller;

import com.bank.transfer.dto.CardTransferDTO;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.service.CardTransferService;
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
@RequestMapping("/card")
public class CardRestController {
    private final CardTransferService cardTransferService;

    @Autowired
    public CardRestController(CardTransferService cardTransferService) {
        this.cardTransferService = cardTransferService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<CardTransferDTO>> getCardTransferById(@PathVariable Long id) {
        return new ResponseEntity<>(cardTransferService.getCardTransferById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CardTransferDTO>> getCardTransfer() {
        final List<CardTransferDTO> cardTransfersDTO = cardTransferService.allCardTransfer();

        if (cardTransfersDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cardTransfersDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addCardTransfer(@RequestBody CardTransferDTO cardTransferDTO) {
        cardTransferService.saveCardTransfer(cardTransferDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping("/{id}")
    public CardTransfer updateCardTransfer(@RequestBody CardTransferDTO cardTransferDTO,
                                           @PathVariable("id") long id) {
        final CardTransfer cardTransferUpdate = cardTransferService.updateCardTransferById(cardTransferDTO, id);
        return cardTransferUpdate;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardTransfer(@PathVariable long id) {
        cardTransferService.deleteCardTransfer(id);
        return ResponseEntity.noContent().build();
    }
}
