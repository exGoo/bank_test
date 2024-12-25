package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryDeletionException;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.exception.HistoryUpdateException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.model.History;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.impl.HistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {
    private static final Long HISTORY_ID = 1L;
    private static final Long HISTORY_ID_TWO = 2L;
    private static final History HISTORY = new History();
    private static final HistoryDto HISTORY_DTO = new HistoryDto();
    private History history;

    @Mock
    private HistoryRepository repository;

    @Mock
    private HistoryMapper mapper;

    @InjectMocks
    private HistoryServiceImpl service;

    @BeforeEach
    public void setUp() {
        history = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();
    }

    @Test
    void getAllHistories() {
        List<History> histories = List.of(new History());
        List<HistoryDto> historyDtoList = List.of(new HistoryDto());

        when(repository.findAll()).thenReturn(histories);
        when(mapper.historiesToDtoList(histories)).thenReturn(historyDtoList);

        List<HistoryDto> actual = service.getAllHistories();

        assertEquals(historyDtoList, actual);
        verify(repository).findAll();
        verify(mapper).historiesToDtoList(histories);
    }

    @Test
    void getHistoryById() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(HISTORY));
        when(mapper.historyToDto(HISTORY)).thenReturn(HISTORY_DTO);

        HistoryDto actual = service.getHistoryById(HISTORY_ID);

        assertEquals(HISTORY_DTO, actual);
        verify(repository).findById(HISTORY_ID);
        verify(mapper).historyToDto(HISTORY);
    }

    @Test
    void getHistoryByIdThrowHistoryNotFoundException() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.empty());

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
        when(repository.save(HISTORY)).thenReturn(HISTORY);
        when(mapper.dtoToHistory(HISTORY_DTO)).thenReturn(HISTORY);

        History actual = service.createHistory(HISTORY_DTO);

        assertEquals(HISTORY, actual);
        verify(repository).save(HISTORY);
        verify(mapper).dtoToHistory(HISTORY_DTO);
    }

    @Test
    void updateHistory() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(HISTORY));
        when(repository.save(any(History.class))).thenReturn(HISTORY);

        History actual = service.updateHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HISTORY, actual);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistory() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(HISTORY));
        when(repository.save(any(History.class))).thenReturn(HISTORY);

        History actual = service.editHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HISTORY, actual);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistoryTwoFieldsWhenAccountAndProfileAuditIds() {
        HistoryDto historyDto = HistoryDto.builder()
                .accountAuditId(HISTORY_ID_TWO)
                .profileAuditId(HISTORY_ID_TWO)
                .build();

        History expected = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID_TWO)
                .accountAuditId(HISTORY_ID_TWO)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID)
                .build();

        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(history));
        when(repository.save(any(History.class))).thenReturn(expected);

        History actual = service.editHistory(HISTORY_ID, historyDto);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getAccountAuditId()).isEqualTo(HISTORY_ID_TWO);
        assertThat(actual.getProfileAuditId()).isEqualTo(HISTORY_ID_TWO);
        assertThat(actual.getAntiFraudAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getTransferAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getPublicBankInfoAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getAuthorizationAuditId()).isEqualTo(HISTORY_ID);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistoryThreeFieldsWhenAntiFraudAndTransferAndPublicBankInfoAuditIds() {
        HistoryDto historyDto = HistoryDto.builder()
                .transferAuditId(HISTORY_ID_TWO)
                .antiFraudAuditId(HISTORY_ID_TWO)
                .publicBankInfoAuditId(HISTORY_ID_TWO)
                .build();

        History expected = History.builder()
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
                .thenReturn(expected);

        History actual = service.editHistory(HISTORY_ID, historyDto);

        assertEquals(expected, actual);

        assertThat(actual.getAccountAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getProfileAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getAntiFraudAuditId()).isEqualTo(HISTORY_ID_TWO);
        assertThat(actual.getTransferAuditId()).isEqualTo(HISTORY_ID_TWO);
        assertThat(actual.getPublicBankInfoAuditId()).isEqualTo(HISTORY_ID_TWO);
        assertThat(actual.getAuthorizationAuditId()).isEqualTo(HISTORY_ID);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistoryOneFieldWhenAuthorizationAuditId() {
        HistoryDto historyDto = HistoryDto.builder()
                .authorizationAuditId(HISTORY_ID_TWO)
                .build();

        History expected = History.builder()
                .id(HISTORY_ID)
                .transferAuditId(HISTORY_ID)
                .profileAuditId(HISTORY_ID)
                .accountAuditId(HISTORY_ID)
                .antiFraudAuditId(HISTORY_ID)
                .publicBankInfoAuditId(HISTORY_ID)
                .authorizationAuditId(HISTORY_ID_TWO)
                .build();

        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(history));
        when(repository.save(any(History.class))).thenReturn(expected);

        History actual = service.editHistory(HISTORY_ID, historyDto);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual.getAccountAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getProfileAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getAntiFraudAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getTransferAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getPublicBankInfoAuditId()).isEqualTo(HISTORY_ID);
        assertThat(actual.getAuthorizationAuditId()).isEqualTo(HISTORY_ID_TWO);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(any(History.class));
    }

    @Test
    void editHistoryThrowsHistoryNotFoundException() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.editHistory(HISTORY_ID, HISTORY_DTO))
                .isInstanceOf(HistoryNotFoundException.class);
        verify(repository).findById(HISTORY_ID);
        verify(repository, never()).save(any());
    }

    @Test
    void editHistoryThrowHistoryUpdateException() {
        when(repository.findById(HISTORY_ID)).thenReturn(Optional.of(HISTORY));
        when(repository.save(HISTORY)).thenThrow(new RuntimeException());

        assertThatThrownBy(() -> service.editHistory(HISTORY_ID, HISTORY_DTO))
                .isInstanceOf(HistoryUpdateException.class);
        verify(repository).findById(HISTORY_ID);
        verify(repository).save(HISTORY);
    }

    @Test
    void deleteHistory() {
        when(repository.existsById(HISTORY_ID)).thenReturn(true);

        service.deleteHistory(HISTORY_ID);

        verify(repository).existsById(HISTORY_ID);
        verify(repository).deleteById(HISTORY_ID);
    }

    @Test
    void deleteHistoryThrowHistoryNotFoundException() {
        when(repository.existsById(HISTORY_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteHistory(HISTORY_ID))
                .isInstanceOf(HistoryNotFoundException.class);
        verify(repository).existsById(HISTORY_ID);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void deleteHistoryThrowHistoryDeletionException() {
        when(repository.existsById(HISTORY_ID)).thenReturn(true);
        doThrow(new RuntimeException()).when(repository).deleteById(HISTORY_ID);

        assertThatThrownBy(() -> service.deleteHistory(HISTORY_ID))
                .isInstanceOf(HistoryDeletionException.class);
        verify(repository).existsById(HISTORY_ID);
        verify(repository).deleteById(HISTORY_ID);
    }
}
