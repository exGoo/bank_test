package com.bank.publicinfo.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Admin {

    private final String username = "admin";

    private final String password = "54321";

    private final String role = "ROLE_ADMIN";

}
