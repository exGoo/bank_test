package com.bank.history.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditTest {
    private static final LocalDateTime TIME = LocalDateTime.now();

    @Test
    void builderTest() {
        Audit audit = Audit.builder()
                .id(1L)
                .entityType("history")
                .operationType("update")
                .createdBy("history.id")
                .modifiedBy("account.id")
                .createdAt(TIME)
                .modifiedAt(TIME.plusDays(10))
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
        assertEquals(1L, audit.getId());
        assertEquals("history", audit.getEntityType());
        assertEquals("update", audit.getOperationType());
        assertEquals("history.id", audit.getCreatedBy());
        assertEquals("account.id", audit.getModifiedBy());
        assertEquals(TIME, audit.getCreatedAt());
        assertEquals(TIME.plusDays(10), audit.getModifiedAt());
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

        audit.setId(1L);
        audit.setEntityType("history");
        audit.setOperationType("update");
        audit.setCreatedBy("history.id");
        audit.setModifiedBy("account.id");
        audit.setCreatedAt(TIME);
        audit.setModifiedAt(TIME.plusDays(10));
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

        assertEquals(1L, audit.getId());
        assertEquals("history", audit.getEntityType());
        assertEquals("update", audit.getOperationType());
        assertEquals("history.id", audit.getCreatedBy());
        assertEquals("account.id", audit.getModifiedBy());
        assertEquals(TIME, audit.getCreatedAt());
        assertEquals(TIME.plusDays(10), audit.getModifiedAt());
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