package com.bank.antifraud.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {

    private final String username = "exZEEST";

    private final String password = "123321";

    private final String role = "ROLE_USER";

}
