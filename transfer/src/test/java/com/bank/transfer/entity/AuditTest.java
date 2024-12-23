package com.bank.transfer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditTest {
    private Audit audit;
    private final String entityJSON = "{\"id\":111,\"accountNumber\":311,\"amount\":347574.34,\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}";

    @BeforeEach
    public void creatNewAudit() {
        audit = new Audit();
    }


    @Test
    void testSettersAndGetters() {
        audit.setId(100L);
        assertEquals(100L, audit.getId());
        audit.setEntityType("AccountTransfer");
        assertEquals("AccountTransfer", audit.getEntityType());
        audit.setOperationType("UPDATE");
        assertEquals("UPDATE", audit.getOperationType());
        audit.setCreatedBy("111");
        assertEquals("111", audit.getCreatedBy());
        audit.setModifiedBy("111");
        assertEquals("111", audit.getModifiedBy());
        audit.setCreatedAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"));
        assertEquals(LocalDateTime.parse("2024-12-22T12:36:46.594599"), audit.getCreatedAt());
        audit.setModifiedAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"));
        assertEquals(LocalDateTime.parse("2024-12-22T12:36:46.594599"), audit.getModifiedAt());
        audit.setNewEntityJson(entityJSON);
        assertEquals(entityJSON, audit.getNewEntityJson());
        audit.setEntityJson(entityJSON);
        assertEquals(entityJSON, audit.getEntityJson());
    }

    @Test
    void testBuilder() {

        Audit audit = Audit.builder()
                .id(1L)
                .entityType("AccountTransfer")
                .operationType("UPDATE")
                .createdBy("111")
                .modifiedBy("111")
                .createdAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"))
                .modifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"))
                .newEntityJson(entityJSON)
                .entityJson(entityJSON)
                .build();

        assertNotNull(audit);
        assertEquals(1L, audit.getId());
        assertEquals("AccountTransfer", audit.getEntityType());
        assertEquals("UPDATE", audit.getOperationType());
        assertEquals("111", audit.getCreatedBy());
        assertEquals("111", audit.getModifiedBy());
        assertEquals(LocalDateTime.parse("2024-12-22T12:36:46.594599"), audit.getCreatedAt());
        assertEquals(LocalDateTime.parse("2024-12-22T12:40:28.096895"), audit.getModifiedAt());
        assertEquals(entityJSON, audit.getEntityJson());
        assertEquals(entityJSON, audit.getNewEntityJson());
    }

    @Test
    void testConstructor() {
        Audit audit = new Audit(1L, "AccountTransfer", "UPDATE", "111", "111",
                LocalDateTime.parse("2024-12-22T12:36:46.594599"),
                LocalDateTime.parse("2024-12-22T12:40:28.096895"), entityJSON, entityJSON);

        assertEquals(1L, audit.getId());
        assertEquals("AccountTransfer", audit.getEntityType());
        assertEquals("UPDATE", audit.getOperationType());
        assertEquals("111", audit.getCreatedBy());
        assertEquals("111", audit.getModifiedBy());
        assertEquals(LocalDateTime.parse("2024-12-22T12:36:46.594599"), audit.getCreatedAt());
        assertEquals(LocalDateTime.parse("2024-12-22T12:40:28.096895"), audit.getModifiedAt());
        assertEquals(entityJSON, audit.getEntityJson());
        assertEquals(entityJSON, audit.getNewEntityJson());
    }

    @Test
    void testToString() {
        audit.setId(1L);
        audit.setEntityType("AccountTransfer");
        audit.setOperationType("UPDATE");
        audit.setCreatedBy("111");
        audit.setModifiedBy("111");
        audit.setCreatedAt(LocalDateTime.parse("2024-12-22T12:36:46.594599"));
        audit.setModifiedAt(LocalDateTime.parse("2024-12-22T12:40:28.096895"));
        audit.setNewEntityJson(entityJSON);
        audit.setEntityJson(entityJSON);

        String expectedString = "Audit(id=1, entityType=AccountTransfer, operationType=UPDATE, " +
                "createdBy=111, modifiedBy=111, createdAt=2024-12-22T12:36:46.594599, modifiedAt=2024-12-22T12:40:28.096895, " +
                "newEntityJson={\"id\":111,\"accountNumber\":311,\"amount\":347574.34,\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5}, " +
                "entityJson={\"id\":111,\"accountNumber\":311,\"amount\":347574.34,\"purpose\":\"ivan ivanov\",\"accountDetailsId\":5})";

        // Проверяем, что результат toString() соответствует ожидаемому значению
        assertEquals(expectedString, audit.toString());
    }

}