package com.bank.profile.controller;

import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Passport;
import com.bank.profile.service.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public void save(@RequestBody AccountDetails accountDetails) {
        System.out.println(accountDetails);
        accountDetailsService.save(accountDetails);
    }
    @GetMapping("/{id}")
    public AccountDetails getById(@PathVariable Long id) {
        return accountDetailsService.findById(id);
    }
    @GetMapping("")
    public List<AccountDetails> getAll() {
        return accountDetailsService.findAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        accountDetailsService.deleteById(id);
    }

}
