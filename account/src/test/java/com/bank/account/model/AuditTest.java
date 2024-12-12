package com.bank.account.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    private Audit audit;

    @BeforeEach
    void setUp() {
        audit = Audit.builder()
                .id(1L)
                .entityType("Test")
                .operationType("TestOperation")
                .createdAt(NOW)
                .createdBy("Test")
                .modifiedAt(NOW)
                .modifiedBy("Test")
                .newEntityJson("""
                            {
                            "id":72,
                            "passportId":43999945,
                            "accountNumber":324344235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":340934
                        }
                        """)
                .entityJson("""
                        {
                            "id":72,
                            "passportId":4324234245,
                            "accountNumber":32234234235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":34534
                        }
                        """)
                .build();
    }

    @Test
    void testAudit() {
        assertNotNull(audit);
        assertEquals(1L, audit.getId());
        assertEquals("Test", audit.getEntityType());
        assertEquals("TestOperation", audit.getOperationType());
        assertEquals(NOW, audit.getCreatedAt());
        assertEquals("Test", audit.getCreatedBy());
        assertEquals(NOW, audit.getModifiedAt());
        assertEquals("Test", audit.getModifiedBy());
        assertEquals("""
                        {
                            "id":72,
                            "passportId":4324234245,
                            "accountNumber":32234234235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":34534
                        }
                        """, audit.getEntityJson());
        assertEquals("""
                            {
                            "id":72,
                            "passportId":43999945,
                            "accountNumber":324344235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":340934
                        }
                        """, audit.getNewEntityJson());
    }

    @Test
    void SetterAndGetterTest() {
        assertNotNull(audit);
        assertEquals(1L, audit.getId());
        assertEquals("Test", audit.getEntityType());
        assertEquals("TestOperation", audit.getOperationType());
        assertEquals(NOW, audit.getCreatedAt());
        assertEquals("Test", audit.getCreatedBy());
        assertEquals(NOW, audit.getModifiedAt());
        assertEquals("Test", audit.getModifiedBy());
        assertEquals("""
                        {
                            "id":72,
                            "passportId":4324234245,
                            "accountNumber":32234234235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":34534
                        }
                        """, audit.getEntityJson());
        assertEquals("""
                            {
                            "id":72,
                            "passportId":43999945,
                            "accountNumber":324344235,
                            "bankDetailsId":545454233,
                            "money":53.00,
                            "negativeBalance":false,
                            "profileId":340934
                        }
                        """, audit.getNewEntityJson());
    }
}