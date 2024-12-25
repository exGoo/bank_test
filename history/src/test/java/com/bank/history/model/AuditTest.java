package com.bank.history.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditTest {
    private static final Long HISTORY_ID = 1L;
    private static final String ENTITY_TYPE = "history";
    private static final String OPERATION_TYPE_UPDATE = "update";
    private static final LocalDateTime CREATED_AT =
            LocalDateTime.of(2024, 12, 12, 12, 12);
    private static final LocalDateTime MODIFIED_AT = LocalDateTime.now();
    private static final String CREATE_BY = "history.id";
    private static final String MODIFIED_BY = "account.id";

    @Test
    void builderTest() {
        Audit audit = Audit.builder()
                .id(HISTORY_ID)
                .entityType(ENTITY_TYPE)
                .operationType(OPERATION_TYPE_UPDATE)
                .createdBy(CREATE_BY)
                .modifiedBy(MODIFIED_BY)
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .newEntityJson("""
                        {
                            "id":1111,
                            "transferAuditId":1111,
                            "profileAuditId":1111,
                            "accountAuditId":1111,
                            "antiFraudAuditId":1111,
                            "publicBankInfoAuditId":1111,
                            "authorizationAuditId":1111
                        }
                        """)
                .entityJson("""
                        {
                            "id":2222,
                            "transferAuditId":2222,
                            "profileAuditId":2222,
                            "accountAuditId":2222,
                            "antiFraudAuditId":2222,
                            "publicBankInfoAuditId":2222,
                            "authorizationAuditId":2222
                        }
                        """)
                .build();

        assertNotNull(audit);
        assertEquals(HISTORY_ID, audit.getId());
        assertEquals(ENTITY_TYPE, audit.getEntityType());
        assertEquals(OPERATION_TYPE_UPDATE, audit.getOperationType());
        assertEquals(CREATE_BY, audit.getCreatedBy());
        assertEquals(MODIFIED_BY, audit.getModifiedBy());
        assertEquals(CREATED_AT, audit.getCreatedAt());
        assertEquals(MODIFIED_AT, audit.getModifiedAt());
        assertEquals("""
                {
                    "id":1111,
                    "transferAuditId":1111,
                    "profileAuditId":1111,
                    "accountAuditId":1111,
                    "antiFraudAuditId":1111,
                    "publicBankInfoAuditId":1111,
                    "authorizationAuditId":1111
                }
                """, audit.getNewEntityJson());
        assertEquals("""
                {
                    "id":2222,
                    "transferAuditId":2222,
                    "profileAuditId":2222,
                    "accountAuditId":2222,
                    "antiFraudAuditId":2222,
                    "publicBankInfoAuditId":2222,
                    "authorizationAuditId":2222
                }
                """, audit.getEntityJson());
    }

    @Test
    void settersAndGettersTest() {
        Audit audit = new Audit();

        audit.setId(HISTORY_ID);
        audit.setEntityType(ENTITY_TYPE);
        audit.setOperationType(OPERATION_TYPE_UPDATE);
        audit.setCreatedBy(CREATE_BY);
        audit.setModifiedBy(MODIFIED_BY);
        audit.setCreatedAt(CREATED_AT);
        audit.setModifiedAt(MODIFIED_AT.plusDays(10));
        audit.setNewEntityJson("""
                {
                    "id":1111,
                    "transferAuditId":1111,
                    "profileAuditId":1111,
                    "accountAuditId":1111,
                    "antiFraudAuditId":1111,
                    "publicBankInfoAuditId":1111,
                    "authorizationAuditId":1111
                }
                """);
        audit.setEntityJson("""
                {
                    "id":2222,
                    "transferAuditId":2222,
                    "profileAuditId":2222,
                    "accountAuditId":2222,
                    "antiFraudAuditId":2222,
                    "publicBankInfoAuditId":2222,
                    "authorizationAuditId":2222
                }
                """);

        assertEquals(HISTORY_ID, audit.getId());
        assertEquals(ENTITY_TYPE, audit.getEntityType());
        assertEquals(OPERATION_TYPE_UPDATE, audit.getOperationType());
        assertEquals(CREATE_BY, audit.getCreatedBy());
        assertEquals(MODIFIED_BY, audit.getModifiedBy());
        assertEquals(CREATED_AT, audit.getCreatedAt());
        assertEquals(MODIFIED_AT, audit.getModifiedAt());
        assertEquals("""
                {
                    "id":1111,
                    "transferAuditId":1111,
                    "profileAuditId":1111,
                    "accountAuditId":1111,
                    "antiFraudAuditId":1111,
                    "publicBankInfoAuditId":1111,
                    "authorizationAuditId":1111
                }
                """, audit.getNewEntityJson());
        assertEquals("""
                {
                    "id":2222,
                    "transferAuditId":2222,
                    "profileAuditId":2222,
                    "accountAuditId":2222,
                    "antiFraudAuditId":2222,
                    "publicBankInfoAuditId":2222,
                    "authorizationAuditId":2222
                }
                """, audit.getEntityJson());
    }
}
