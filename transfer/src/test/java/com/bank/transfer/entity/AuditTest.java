package com.bank.transfer.entity;

import org.junit.jupiter.api.Test;

import static com.bank.transfer.ResourcesForTests.AUDIT_TO_STRING;
import static com.bank.transfer.ResourcesForTests.CREATED_AT;
import static com.bank.transfer.ResourcesForTests.CREATED_BY;
import static com.bank.transfer.ResourcesForTests.ENTITY_JSON;
import static com.bank.transfer.ResourcesForTests.ENTITY_TYPE_C_T;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.MODIFIED_AT;
import static com.bank.transfer.ResourcesForTests.MODIFIED_BY;
import static com.bank.transfer.ResourcesForTests.NEW_ENTITY_JSON;
import static com.bank.transfer.ResourcesForTests.OPERATION_TYPE_CREATE;
import static com.bank.transfer.ResourcesForTests.OPERATION_TYPE_UPDATE;
import static com.bank.transfer.ResourcesForTests.audit1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditTest {

    @Test
    void testSettersAndGetters() {
        audit1.setId(ID_1);
        assertEquals(ID_1, audit1.getId());
        audit1.setEntityType(ENTITY_TYPE_C_T);
        assertEquals(ENTITY_TYPE_C_T, audit1.getEntityType());
        audit1.setOperationType(OPERATION_TYPE_UPDATE);
        assertEquals(OPERATION_TYPE_UPDATE, audit1.getOperationType());
        audit1.setCreatedBy(CREATED_BY);
        assertEquals(CREATED_BY, audit1.getCreatedBy());
        audit1.setModifiedBy(MODIFIED_BY);
        assertEquals(MODIFIED_BY, audit1.getModifiedBy());
        audit1.setCreatedAt(CREATED_AT);
        assertEquals(CREATED_AT, audit1.getCreatedAt());
        audit1.setModifiedAt(MODIFIED_AT);
        assertEquals(MODIFIED_AT, audit1.getModifiedAt());
        audit1.setNewEntityJson(ENTITY_JSON);
        assertEquals(ENTITY_JSON, audit1.getEntityJson());
        audit1.setEntityJson(NEW_ENTITY_JSON);
        assertEquals(NEW_ENTITY_JSON, audit1.getEntityJson());
    }

    @Test
    void testBuilder() {
        assertNotNull(audit1);
        assertEquals(ID_1, audit1.getId());
        assertEquals(ENTITY_TYPE_C_T, audit1.getEntityType());
        assertEquals(OPERATION_TYPE_UPDATE, audit1.getOperationType());
        assertEquals(CREATED_BY, audit1.getCreatedBy());
        assertEquals(MODIFIED_BY, audit1.getModifiedBy());
        assertEquals(CREATED_AT, audit1.getCreatedAt());
        assertEquals(MODIFIED_AT, audit1.getModifiedAt());
        assertEquals(ENTITY_JSON, audit1.getEntityJson());
        assertEquals(NEW_ENTITY_JSON, audit1.getNewEntityJson());
    }

    @Test
    void testConstructor() {
        assertEquals(ID_1, audit1.getId());
        assertEquals(ENTITY_TYPE_C_T, audit1.getEntityType());
        assertEquals(OPERATION_TYPE_UPDATE, audit1.getOperationType());
        assertEquals(CREATED_BY, audit1.getCreatedBy());
        assertEquals(MODIFIED_BY, audit1.getModifiedBy());
        assertEquals(CREATED_AT, audit1.getCreatedAt());
        assertEquals(MODIFIED_AT, audit1.getModifiedAt());
        assertEquals(ENTITY_JSON, audit1.getEntityJson());
        assertEquals(NEW_ENTITY_JSON, audit1.getNewEntityJson());
    }

    @Test
    void testToString() {
        audit1.setId(ID_1);
        audit1.setEntityType(ENTITY_TYPE_C_T);
        audit1.setOperationType(OPERATION_TYPE_CREATE);
        audit1.setCreatedBy(CREATED_BY);
        audit1.setModifiedBy(MODIFIED_BY);
        audit1.setCreatedAt(CREATED_AT);
        audit1.setModifiedAt(MODIFIED_AT);
        audit1.setNewEntityJson(ENTITY_JSON);
        audit1.setEntityJson(NEW_ENTITY_JSON);

        assertEquals(AUDIT_TO_STRING, audit1.toString());
    }

}