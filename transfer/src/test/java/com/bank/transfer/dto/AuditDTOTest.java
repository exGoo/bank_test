package com.bank.transfer.dto;

import org.junit.jupiter.api.Test;

import static com.bank.transfer.ResourcesForTests.CREATED_AT;
import static com.bank.transfer.ResourcesForTests.CREATED_BY;
import static com.bank.transfer.ResourcesForTests.ENTITY_JSON;
import static com.bank.transfer.ResourcesForTests.ENTITY_TYPE_A_T;
import static com.bank.transfer.ResourcesForTests.MODIFIED_AT;
import static com.bank.transfer.ResourcesForTests.MODIFIED_BY;
import static com.bank.transfer.ResourcesForTests.NEW_ENTITY_JSON;
import static com.bank.transfer.ResourcesForTests.OPERATION_TYPE_CREATE;
import static com.bank.transfer.ResourcesForTests.OPERATION_TYPE_UPDATE;
import static com.bank.transfer.ResourcesForTests.auditDTO;
import static com.bank.transfer.ResourcesForTests.auditDTO1;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditDTOTest {

    @Test
    void testAuditDTOConstructor() {
        assertEquals(ENTITY_TYPE_A_T, auditDTO1.getEntityType());
        assertEquals(OPERATION_TYPE_UPDATE, auditDTO1.getOperationType());
        assertEquals(CREATED_BY, auditDTO1.getCreatedBy());
        assertEquals(MODIFIED_BY, auditDTO1.getModifiedBy());
        assertEquals(CREATED_AT, auditDTO1.getCreatedAt());
        assertEquals(MODIFIED_AT, auditDTO1.getModifiedAt());
        assertEquals(NEW_ENTITY_JSON, auditDTO1.getNewEntityJson());
        assertEquals(ENTITY_JSON, auditDTO1.getEntityJson());
    }

    @Test
    void testNoArgsConstructor() {
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
        auditDTO1.setEntityType(ENTITY_TYPE_A_T);
        auditDTO1.setOperationType(OPERATION_TYPE_CREATE);
        auditDTO1.setCreatedBy(CREATED_BY);
        auditDTO1.setModifiedBy(MODIFIED_BY);
        auditDTO1.setCreatedAt(CREATED_AT);
        auditDTO1.setModifiedAt(MODIFIED_AT);
        auditDTO1.setNewEntityJson(NEW_ENTITY_JSON);
        auditDTO1.setEntityJson(ENTITY_JSON);

        assertEquals(ENTITY_TYPE_A_T, auditDTO1.getEntityType());
        assertEquals(OPERATION_TYPE_CREATE, auditDTO1.getOperationType());
        assertEquals(CREATED_BY, auditDTO1.getCreatedBy());
        assertEquals(MODIFIED_BY, auditDTO1.getModifiedBy());
        assertEquals(CREATED_AT, auditDTO1.getCreatedAt());
        assertEquals(MODIFIED_AT, auditDTO1.getModifiedAt());
        assertEquals(NEW_ENTITY_JSON, auditDTO1.getNewEntityJson());
        assertEquals(ENTITY_JSON, auditDTO1.getEntityJson());
    }
}
