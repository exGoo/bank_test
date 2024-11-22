package com.bank.history.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryDtoTest {

    @Test
    void builderTest() {
        HistoryDto historyDto = HistoryDto.builder()
                .id(1000L)
                .transferAuditId(1001L)
                .profileAuditId(1002L)
                .accountAuditId(1003L)
                .antiFraudAuditId(1004L)
                .publicBankInfoAuditId(1005L)
                .authorizationAuditId(1006L)
                .build();

        assertNotNull(historyDto);
        assertEquals(1000L, historyDto.getId());
        assertEquals(1001L, historyDto.getTransferAuditId());
        assertEquals(1002L, historyDto.getProfileAuditId());
        assertEquals(1003L, historyDto.getAccountAuditId());
        assertEquals(1004L, historyDto.getAntiFraudAuditId());
        assertEquals(1005L, historyDto.getPublicBankInfoAuditId());
        assertEquals(1006L, historyDto.getAuthorizationAuditId());
    }

    @Test
    void settersAndGettersTest() {
        HistoryDto historyDto = new HistoryDto();

        historyDto.setTransferAuditId(1001L);
        historyDto.setProfileAuditId(1002L);
        historyDto.setAccountAuditId(1003L);
        historyDto.setAntiFraudAuditId(1004L);
        historyDto.setPublicBankInfoAuditId(1005L);
        historyDto.setAuthorizationAuditId(1006L);

        assertEquals(1001L, historyDto.getTransferAuditId());
        assertEquals(1002L, historyDto.getProfileAuditId());
        assertEquals(1003L, historyDto.getAccountAuditId());
        assertEquals(1004L, historyDto.getAntiFraudAuditId());
        assertEquals(1005L, historyDto.getPublicBankInfoAuditId());
        assertEquals(1006L, historyDto.getAuthorizationAuditId());
    }
}