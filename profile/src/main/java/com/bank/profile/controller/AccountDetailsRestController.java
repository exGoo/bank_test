package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void save(@RequestBody AccountDetailsDto accountDetails) {
        accountDetailsService.save(accountDetails);
    }

    @GetMapping("/{id}")
    public AccountDetailsDto getById(@PathVariable Long id) {
        return accountDetailsService.findById(id);
    }

    @GetMapping("")
    public List<AccountDetailsDto> getAll() {
        return accountDetailsService.findAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id,@RequestBody AccountDetailsDto accountDetails) {
        accountDetailsService.update(id, accountDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        accountDetailsService.deleteById(id);
    }

}
