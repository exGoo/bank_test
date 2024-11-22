package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryMapperTest {
    public static final Long ONE = 1111L;
    public static final Long TWO = 2222L;
    public static final Long THREE = 3333L;
    public static final Long FOUR = 4444L;
    public static final Long FIVE = 5555L;
    public static final Long SIX = 6666L;
    public static final Long SEVEN = 7777L;

    private static final HistoryMapper MAPPER =
            Mappers.getMapper(HistoryMapper.class);

    @Test
    void historyToDtoTest() {
        History history = History.builder()
                .id(ONE)
                .transferAuditId(TWO)
                .profileAuditId(THREE)
                .accountAuditId(FOUR)
                .antiFraudAuditId(FIVE)
                .publicBankInfoAuditId(SIX)
                .authorizationAuditId(SEVEN)
                .build();

        HistoryDto historyDto = MAPPER.historyToDto(history);

        assertNotNull(historyDto);
        assertEquals(ONE, historyDto.getId());
        assertEquals(TWO, historyDto.getTransferAuditId());
        assertEquals(THREE, historyDto.getProfileAuditId());
        assertEquals(FOUR, historyDto.getAccountAuditId());
        assertEquals(FIVE, historyDto.getAntiFraudAuditId());
        assertEquals(SIX, historyDto.getPublicBankInfoAuditId());
        assertEquals(SEVEN, historyDto.getAuthorizationAuditId());
    }

    @Test
    void dtoToHistoryTest() {
        HistoryDto historyDto = HistoryDto.builder()
                .id(ONE)
                .transferAuditId(TWO)
                .profileAuditId(THREE)
                .accountAuditId(FOUR)
                .antiFraudAuditId(FIVE)
                .publicBankInfoAuditId(SIX)
                .authorizationAuditId(SEVEN)
                .build();

        History actualResult = MAPPER.dtoToHistory(historyDto);

        assertNotNull(actualResult);
        assertEquals(ONE, actualResult.getId());
        assertEquals(TWO, actualResult.getTransferAuditId());
        assertEquals(THREE, actualResult.getProfileAuditId());
        assertEquals(FOUR, actualResult.getAccountAuditId());
        assertEquals(FIVE, actualResult.getAntiFraudAuditId());
        assertEquals(SIX, actualResult.getPublicBankInfoAuditId());
        assertEquals(SEVEN, actualResult.getAuthorizationAuditId());
    }

    @Test
    void historiesToDtoListTest() {
        List<History> histories = List.of(
                History.builder()
                        .id(ONE)
                        .transferAuditId(ONE)
                        .profileAuditId(ONE)
                        .accountAuditId(ONE)
                        .antiFraudAuditId(ONE)
                        .publicBankInfoAuditId(ONE)
                        .authorizationAuditId(ONE)
                        .build(),
                History.builder()
                        .id(TWO)
                        .transferAuditId(TWO)
                        .profileAuditId(TWO)
                        .accountAuditId(TWO)
                        .antiFraudAuditId(TWO)
                        .publicBankInfoAuditId(TWO)
                        .authorizationAuditId(TWO)
                        .build());

        List<HistoryDto> actualResult = MAPPER.historiesToDtoList(histories);

        assertNotNull(actualResult);
        assertEquals(2, actualResult.size());
        assertEquals(ONE, actualResult.get(0).getId());
        assertEquals(ONE, actualResult.get(0).getTransferAuditId());
        assertEquals(ONE, actualResult.get(0).getProfileAuditId());
        assertEquals(ONE, actualResult.get(0).getAccountAuditId());
        assertEquals(ONE, actualResult.get(0).getAntiFraudAuditId());
        assertEquals(ONE, actualResult.get(0).getPublicBankInfoAuditId());
        assertEquals(ONE, actualResult.get(0).getAuthorizationAuditId());

        assertEquals(TWO, actualResult.get(1).getId());
        assertEquals(TWO, actualResult.get(1).getTransferAuditId());
        assertEquals(TWO, actualResult.get(1).getProfileAuditId());
        assertEquals(TWO, actualResult.get(1).getAccountAuditId());
        assertEquals(TWO, actualResult.get(1).getAntiFraudAuditId());
        assertEquals(TWO, actualResult.get(1).getPublicBankInfoAuditId());
        assertEquals(TWO, actualResult.get(1).getAuthorizationAuditId());
    }
}