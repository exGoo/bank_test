package com.bank.history.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryTest {

    @ParameterizedTest
    @CsvSource({
            "1000, 1001, 1002, 1003, 1004, 1005, 1006",
            "2000, 2001, 2002, 2003, 2004, 2005, 2006",
            "3000, 3001, 3002, 3003, 3004, 3005, 3006"
    })
    void builderTest(long id, long transferAuditId, long profileAuditId, long accountAuditId,
                     long antiFraudAuditId, long publicBankInfoAuditId, long authorizationAuditId) {
        History history = History.builder()
                .id(id)
                .transferAuditId(transferAuditId)
                .profileAuditId(profileAuditId)
                .accountAuditId(accountAuditId)
                .antiFraudAuditId(antiFraudAuditId)
                .publicBankInfoAuditId(publicBankInfoAuditId)
                .authorizationAuditId(authorizationAuditId)
                .build();

        assertNotNull(history);
        assertEquals(id, history.getId());
        assertEquals(transferAuditId, history.getTransferAuditId());
        assertEquals(profileAuditId, history.getProfileAuditId());
        assertEquals(accountAuditId, history.getAccountAuditId());
        assertEquals(antiFraudAuditId, history.getAntiFraudAuditId());
        assertEquals(publicBankInfoAuditId, history.getPublicBankInfoAuditId());
        assertEquals(authorizationAuditId, history.getAuthorizationAuditId());
    }

    @ParameterizedTest
    @CsvSource({
            "1001, 1002, 1003, 1004, 1005, 1006",
            "2001, 2002, 2003, 2004, 2005, 2006",
            "3001, 3002, 3003, 3004, 3005, 3006"
    })
    void settersAndGettersTest(long transferAuditId, long profileAuditId, long accountAuditId,
                               long antiFraudAuditId, long publicBankInfoAuditId, long authorizationAuditId) {
        History history = new History();

        history.setTransferAuditId(transferAuditId);
        history.setProfileAuditId(profileAuditId);
        history.setAccountAuditId(accountAuditId);
        history.setAntiFraudAuditId(antiFraudAuditId);
        history.setPublicBankInfoAuditId(publicBankInfoAuditId);
        history.setAuthorizationAuditId(authorizationAuditId);

        assertEquals(transferAuditId, history.getTransferAuditId());
        assertEquals(profileAuditId, history.getProfileAuditId());
        assertEquals(accountAuditId, history.getAccountAuditId());
        assertEquals(antiFraudAuditId, history.getAntiFraudAuditId());
        assertEquals(publicBankInfoAuditId, history.getPublicBankInfoAuditId());
        assertEquals(authorizationAuditId, history.getAuthorizationAuditId());
    }
}
