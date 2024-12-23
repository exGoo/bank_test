package com.bank.transfer.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditDTOTest {
    private AuditDTO auditDTO;

    @Test
    void testAuditDTOConstructor() {
        // Given
        String entityType = "AccountTransfer";
        String operationType = "UPDATE";
        String createdBy = "user123";
        String modifiedBy = "user456";
        LocalDateTime createdAt = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2023, 10, 1, 12, 0);
        String newEntityJson = "{\"id\":1,\"type\":\"AccountTransfer\"}";
        String entityJson = "{\"id\":1,\"type\":\"AccountTransfer\",\"status\":\"active\"}";

        // When
        auditDTO = new AuditDTO(entityType, operationType, createdBy, modifiedBy, createdAt, modifiedAt, newEntityJson, entityJson);

        // Then
        assertEquals(entityType, auditDTO.getEntityType());
        assertEquals(operationType, auditDTO.getOperationType());
        assertEquals(createdBy, auditDTO.getCreatedBy());
        assertEquals(modifiedBy, auditDTO.getModifiedBy());
        assertEquals(createdAt, auditDTO.getCreatedAt());
        assertEquals(modifiedAt, auditDTO.getModifiedAt());
        assertEquals(newEntityJson, auditDTO.getNewEntityJson());
        assertEquals(entityJson, auditDTO.getEntityJson());
    }

        @Test
        void testNoArgsConstructor() {
            auditDTO = new AuditDTO();
            // Then
            assertEquals(null, auditDTO.getEntityType());
            assertEquals(null, auditDTO.getOperationType());
            assertEquals(null, auditDTO.getCreatedBy());
            assertEquals(null, auditDTO.getModifiedBy());
            assertEquals(null, auditDTO.getCreatedAt());
            assertEquals(null, auditDTO.getModifiedAt());
            assertEquals(null, auditDTO.getNewEntityJson());
            assertEquals(null, auditDTO.getEntityJson());
        }
    @Test
    void testSetters() {
        auditDTO = new AuditDTO();
        String entityType = "AccountTransfer";
    String operationType = "UPDATE";
    String createdBy = "user123";
    String modifiedBy = "user456";
    LocalDateTime createdAt = LocalDateTime.of(2023, 10, 1, 10, 0);
    LocalDateTime modifiedAt = LocalDateTime.of(2023, 10, 1, 12, 0);
    String newEntityJson = "{\"id\":1,\"type\":\"AccountTransfer\"}";
    String entityJson = "{\"id\":1,\"type\":\"AccountTransfer\",\"status\":\"active\"}";

    // When
        auditDTO.setEntityType(entityType);
        auditDTO.setOperationType(operationType);
        auditDTO.setCreatedBy(createdBy);
        auditDTO.setModifiedBy(modifiedBy);
        auditDTO.setCreatedAt(createdAt);
        auditDTO.setModifiedAt(modifiedAt);
        auditDTO.setNewEntityJson(newEntityJson);
        auditDTO.setEntityJson(entityJson);

    // Then
    assertEquals(entityType, auditDTO.getEntityType());
    assertEquals(operationType, auditDTO.getOperationType());
    assertEquals(createdBy, auditDTO.getCreatedBy());
    assertEquals(modifiedBy, auditDTO.getModifiedBy());
    assertEquals(createdAt, auditDTO.getCreatedAt());
    assertEquals(modifiedAt, auditDTO.getModifiedAt());
    assertEquals(newEntityJson, auditDTO.getNewEntityJson());
    assertEquals(entityJson, auditDTO.getEntityJson());
}

    }