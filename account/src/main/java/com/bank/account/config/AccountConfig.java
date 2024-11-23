package com.bank.account.config;

import com.bank.account.mapper.AccountMapper;
import com.bank.account.mapper.AccountMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountMapper accountMapper() {
        return new AccountMapperImpl();
    }

}
