package com.bank.account.service.impl;

import com.bank.account.dao.AuditDao;
import com.bank.account.model.Audit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {
    private static final LocalDateTime NOW = LocalDateTime.now();

    @InjectMocks
    private AuditServiceImpl auditService;

    @Mock
    private AuditDao auditDao;

    private Audit audit;
    private Audit auditTwo;

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

        auditTwo = Audit.builder()
                .id(2L)
                .entityType("TestTwo")
                .operationType("TestOperation")
                .createdAt(NOW)
                .createdBy("TestTwo")
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
    void save() {
        auditService.save(audit);
        verify(auditDao, times(1)).save(audit);
    }

    @Test
    void findLastAuditByUser() {
        when(auditDao.findLastAuditByUser(auditTwo.getCreatedBy())).thenReturn(auditTwo);
        Audit auditResult = auditService.findLastAuditByUser(auditTwo.getCreatedBy());

        assertNotNull(auditResult);
        assertEquals(2L, auditResult.getId());
        assertEquals("TestTwo", auditResult.getEntityType());
    }
}