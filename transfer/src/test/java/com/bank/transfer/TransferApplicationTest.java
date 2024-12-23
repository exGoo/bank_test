package com.bank.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TransferApplicationTest {

    @Test
    void contextLoads() {

    }
    @Test
    void mainMethodRuns() {
        // Проверка, что метод main запускает приложение без исключений
        TransferApplication.main(new String[]{});
    }
}
