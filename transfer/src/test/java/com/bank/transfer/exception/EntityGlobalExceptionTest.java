package com.bank.transfer.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityGlobalExceptionTest {

    @Test
    public void testConstructorAndGetter() {
        String testMessage = "This is a test exception message.";
        EntityGlobalException exception = new EntityGlobalException(testMessage);


        assertEquals(testMessage, exception.getMessage(), "The message should match the test message.");
    }
}
