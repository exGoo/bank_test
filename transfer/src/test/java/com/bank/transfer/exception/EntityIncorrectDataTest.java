package com.bank.transfer.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityIncorrectDataTest {
    @Test
    public void testConstructorAndGetter() {
        String testErrorInfo = "This is a test error message.";
        EntityIncorrectData entity = new EntityIncorrectData(testErrorInfo);

        // Проверяем, что errorInfo правильно устанавливается и возвращается
        assertEquals(testErrorInfo, entity.getErrorInfo(), "The errorInfo should match the test error message.");
    }

    @Test
    public void testSetter() {
        EntityIncorrectData entity = new EntityIncorrectData("Initial error message.");
        String newErrorInfo = "Updated error message.";

        // Устанавливаем новое значение
        entity.setErrorInfo(newErrorInfo);

        // Проверяем, что новое значение правильно устанавливается
        assertEquals(newErrorInfo, entity.getErrorInfo(), "The errorInfo should match the updated error message.");
    }
}