package com.bank.antifraud.util.users;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Admin {

    private final String username = "admin";

    private final String password = "12345";

    private final String role = "ROLE_ADMIN";
}
