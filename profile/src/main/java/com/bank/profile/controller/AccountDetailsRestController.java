package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/AccountDetails")
public class AccountDetailsRestController {

    AccountDetailsService accountDetailsService;

    @Autowired
    public AccountDetailsRestController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @PostMapping("")
    public ResponseEntity<AccountDetailsDto> save(@RequestBody @Valid AccountDetailsDto accountDetails) {
       AccountDetailsDto result = accountDetailsService.save(accountDetails);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountDetailsService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<AccountDetailsDto>> getAll() {
        return ResponseEntity.ok(accountDetailsService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid AccountDetailsDto accountDetails) {
        accountDetailsService.update(id, accountDetails);
        return ResponseEntity.ok("Account details updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        accountDetailsService.deleteById(id);
        return ResponseEntity.ok("Account details deleted");

    }

}
