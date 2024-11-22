package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.model.History;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.impl.HistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {
    private static final Long HISTORY_ID = 1L;
    private static final Long HISTORY_ID_TWO = 2L;
    private static final History HISTORY = new History();
    private static final HistoryDto HISTORY_DTO = new HistoryDto();

    @Mock
    private HistoryRepository repository;

    @Mock
    private HistoryMapper mapper;

    @InjectMocks
    private HistoryServiceImpl service;

    @Test
    void getAllHistories() {
        List<History> histories = List.of(new History());
        List<HistoryDto> historyDtoList = List.of(new HistoryDto());

        when(repository.findAll())
                .thenReturn(histories);
        when(mapper.historiesToDtoList(histories))
                .thenReturn(historyDtoList);

        List<HistoryDto> actualResult = service.getAllHistories();

        assertEquals(historyDtoList, actualResult);
        verify(repository).findAll();
        verify(mapper).historiesToDtoList(histories);
    }

    @Test
    void getHistoryById() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(HISTORY));
        when(mapper.historyToDto(HISTORY))
                .thenReturn(HISTORY_DTO);

        HistoryDto actualResult = service.getHistoryById(HISTORY_ID);

        assertEquals(HISTORY_DTO, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(mapper).historyToDto(HISTORY);
    }

    @Test
    void getHistoryByIdThrowHistoryNotFoundException() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.empty());

        HistoryNotFoundException exception =
                assertThrows(HistoryNotFoundException.class,
                        () -> service.getHistoryById(HISTORY_ID));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("История не найдена", exception.getReason());

        verify(repository).findById(HISTORY_ID);
        verifyNoInteractions(mapper);
    }

    @Test
    void createHistory() {
        when(repository.save(HISTORY))
                .thenReturn(HISTORY);
        when(mapper.dtoToHistory(HISTORY_DTO))
                .thenReturn(HISTORY);

        History actualResult = service.createHistory(HISTORY_DTO);

        assertEquals(HISTORY, actualResult);
        verify(repository).save(HISTORY);
        verify(mapper).dtoToHistory(HISTORY_DTO);
    }

    @Test
    void updateHistory() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(HISTORY));
        when(repository.save(any(History.class)))
                .thenReturn(HISTORY);

        History actualResult = service.updateHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HISTORY, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistory() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(HISTORY));
        when(repository.save(any(History.class)))
                .thenReturn(HISTORY);

        History actualResult = service.editHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HISTORY, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistoryTwoFieldsWhenAccountAndProfileAuditIds() {
        History history = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();
        HistoryDto historyDto = HistoryDto.builder()
                .accountAuditId(HISTORY_ID_TWO)
                .profileAuditId(HISTORY_ID_TWO)
                .build();

        History expectedResult = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID_TWO)
                .accountAuditId(HISTORY_ID_TWO)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();

        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(history));
        when(repository.save(any(History.class)))
                .thenReturn(expectedResult);

        History actualResult = service.editHistory(HISTORY_ID, historyDto);

        assertEquals(expectedResult, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(argThat(
                field -> field.getAccountAuditId().equals(HISTORY_ID_TWO)
                        && field.getProfileAuditId().equals(HISTORY_ID_TWO)
                        && field.getTransferAuditId().equals(HISTORY_ID)
                        && field.getAntiFraudAuditId().equals(HISTORY_ID)
                        && field.getPublicBankInfoAuditId().equals(HISTORY_ID)
                        && field.getAuthorizationAuditId().equals(HISTORY_ID)
        ));
    }

    @Test
    void editHistoryThreeFieldsWhenAntiFraudAndTransferAndPublicBankInfoAuditIds() {
        History history = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();
        HistoryDto historyDto = HistoryDto.builder()
                .transferAuditId(HISTORY_ID_TWO)
                .antiFraudAuditId(HISTORY_ID_TWO)
                .publicBankInfoAuditId(HISTORY_ID_TWO)
                .build();

        History expectedResult = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID_TWO)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID_TWO)
                .publicBankInfoAuditId(HISTORY_ID_TWO)
                .authorizationAuditId(HISTORY_ID)
                .build();

        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(history));
        when(repository.save(any(History.class)))
                .thenReturn(expectedResult);

        History actualResult = service.editHistory(HISTORY_ID, historyDto);

        assertEquals(expectedResult, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(argThat(
                field -> field.getAccountAuditId().equals(HISTORY_ID)
                        && field.getProfileAuditId().equals(HISTORY_ID)
                        && field.getTransferAuditId().equals(HISTORY_ID_TWO)
                        && field.getAntiFraudAuditId().equals(HISTORY_ID_TWO)
                        && field.getPublicBankInfoAuditId().equals(HISTORY_ID_TWO)
                        && field.getAuthorizationAuditId().equals(HISTORY_ID)
        ));
    }

    @Test
    void editHistoryOneFieldWhenAuthorizationAuditId() {
        History history = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();
        HistoryDto historyDto = HistoryDto.builder()
                .authorizationAuditId(HISTORY_ID_TWO)
                .build();

        History expectedResult = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID_TWO)
                .build();

        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(history));
        when(repository.save(any(History.class)))
                .thenReturn(expectedResult);

        History actualResult = service.editHistory(HISTORY_ID, historyDto);

        assertEquals(expectedResult, actualResult);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(argThat(
                field -> field.getAccountAuditId().equals(HISTORY_ID)
                        && field.getProfileAuditId().equals(HISTORY_ID)
                        && field.getTransferAuditId().equals(HISTORY_ID)
                        && field.getAntiFraudAuditId().equals(HISTORY_ID)
                        && field.getPublicBankInfoAuditId().equals(HISTORY_ID)
                        && field.getAuthorizationAuditId().equals(HISTORY_ID_TWO)
        ));
    }

    @Test
    void deleteHistory() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.of(new History()));

        service.deleteHistory(HISTORY_ID);

        verify(repository).findById(HISTORY_ID);
        verify(repository).deleteById(HISTORY_ID);
    }

    @Test
    void deleteHistoryThrowHistoryNotFoundException() {
        when(repository.findById(HISTORY_ID))
                .thenReturn(Optional.empty());

        assertThrows(HistoryNotFoundException.class,
                () -> service.deleteHistory(HISTORY_ID));

        verify(repository).findById(HISTORY_ID);
        verify(repository, never()).deleteById(anyLong());
    }
}