package com.bank.history.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryTest {

    @Test
    void builderTest() {
        History history = History.builder()
                .id(1000L)
                .transferAuditId(1001L)
                .profileAuditId(1002L)
                .accountAuditId(1003L)
                .antiFraudAuditId(1004L)
                .publicBankInfoAuditId(1005L)
                .authorizationAuditId(1006L)
                .build();

        assertNotNull(history);
        assertEquals(1000L, history.getId());
        assertEquals(1001L, history.getTransferAuditId());
        assertEquals(1002L, history.getProfileAuditId());
        assertEquals(1003L, history.getAccountAuditId());
        assertEquals(1004L, history.getAntiFraudAuditId());
        assertEquals(1005L, history.getPublicBankInfoAuditId());
        assertEquals(1006L, history.getAuthorizationAuditId());
    }

    @Test
    void settersAndGettersTest() {
        History history = new History();

        history.setTransferAuditId(1001L);
        history.setProfileAuditId(1002L);
        history.setAccountAuditId(1003L);
        history.setAntiFraudAuditId(1004L);
        history.setPublicBankInfoAuditId(1005L);
        history.setAuthorizationAuditId(1006L);

        assertEquals(1001L, history.getTransferAuditId());
        assertEquals(1002L, history.getProfileAuditId());
        assertEquals(1003L, history.getAccountAuditId());
        assertEquals(1004L, history.getAntiFraudAuditId());
        assertEquals(1005L, history.getPublicBankInfoAuditId());
        assertEquals(1006L, history.getAuthorizationAuditId());
    }
}