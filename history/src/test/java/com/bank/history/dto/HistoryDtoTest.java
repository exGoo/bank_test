package com.bank.history.dto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryDtoTest {

    @ParameterizedTest
    @CsvSource({
            "1000, 1001, 1002, 1003, 1004, 1005, 1006",
            "2000, 2001, 2002, 2003, 2004, 2005, 2006",
            "3000, 3001, 3002, 3003, 3004, 3005, 3006"
    })
    void builderTest(long id, long transferAuditId, long profileAuditId, long accountAuditId,
                     long antiFraudAuditId, long publicBankInfoAuditId, long authorizationAuditId) {
        HistoryDto historyDto = HistoryDto.builder()
                .id(id)
                .transferAuditId(transferAuditId)
                .profileAuditId(profileAuditId)
                .accountAuditId(accountAuditId)
                .antiFraudAuditId(antiFraudAuditId)
                .publicBankInfoAuditId(publicBankInfoAuditId)
                .authorizationAuditId(authorizationAuditId)
                .build();

        assertNotNull(historyDto);
        assertEquals(id, historyDto.getId());
        assertEquals(transferAuditId, historyDto.getTransferAuditId());
        assertEquals(profileAuditId, historyDto.getProfileAuditId());
        assertEquals(accountAuditId, historyDto.getAccountAuditId());
        assertEquals(antiFraudAuditId, historyDto.getAntiFraudAuditId());
        assertEquals(publicBankInfoAuditId, historyDto.getPublicBankInfoAuditId());
        assertEquals(authorizationAuditId, historyDto.getAuthorizationAuditId());
    }

    @ParameterizedTest
    @CsvSource({
            "1001, 1002, 1003, 1004, 1005, 1006",
            "2001, 2002, 2003, 2004, 2005, 2006",
            "3001, 3002, 3003, 3004, 3005, 3006"
    })
    void settersAndGettersTest(long transferAuditId, long profileAuditId, long accountAuditId,
                               long antiFraudAuditId, long publicBankInfoAuditId, long authorizationAuditId) {
        HistoryDto historyDto = new HistoryDto();

        historyDto.setTransferAuditId(transferAuditId);
        historyDto.setProfileAuditId(profileAuditId);
        historyDto.setAccountAuditId(accountAuditId);
        historyDto.setAntiFraudAuditId(antiFraudAuditId);
        historyDto.setPublicBankInfoAuditId(publicBankInfoAuditId);
        historyDto.setAuthorizationAuditId(authorizationAuditId);

        assertEquals(transferAuditId, historyDto.getTransferAuditId());
        assertEquals(profileAuditId, historyDto.getProfileAuditId());
        assertEquals(accountAuditId, historyDto.getAccountAuditId());
        assertEquals(antiFraudAuditId, historyDto.getAntiFraudAuditId());
        assertEquals(publicBankInfoAuditId, historyDto.getPublicBankInfoAuditId());
        assertEquals(authorizationAuditId, historyDto.getAuthorizationAuditId());
    }
}
