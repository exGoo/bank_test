package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.model.History;
import com.bank.history.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {
    private static final Long HISTORY_ID = 1L;
    private static final History HISTORY = new History();
    private static final HistoryDto HISTORY_DTO = new HistoryDto();

    @Mock
    private HistoryService service;

    @InjectMocks
    private HistoryController controller;

    @Test
    void getAllHistories() {
        List<HistoryDto> histories = List.of(HISTORY_DTO);

        when(service.getAllHistories())
                .thenReturn(histories);

        ResponseEntity<List<HistoryDto>> actualResult =
                controller.getAllHistories();

        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(histories, actualResult.getBody());
        verify(service).getAllHistories();
    }

    @Test
    void getHistoryById() {
        when(service.getHistoryById(HISTORY_ID))
                .thenReturn(HISTORY_DTO);

        ResponseEntity<HistoryDto> actualResult =
                controller.getHistoryById(HISTORY_ID);

        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(HISTORY_DTO, actualResult.getBody());
        verify(service).getHistoryById(HISTORY_ID);
    }

    @Test
    void getHistoryByIdThrowHistoryNotFoundException() {
        when(service.getHistoryById(HISTORY_ID))
                .thenThrow(new HistoryNotFoundException());

        assertThrows(HistoryNotFoundException.class,
                () -> controller.getHistoryById(HISTORY_ID));
        verify(service).getHistoryById(HISTORY_ID);
    }

    @Test
    void createHistory() {
        when(service.createHistory(HISTORY_DTO))
                .thenReturn(HISTORY);

        ResponseEntity<History> actualResult =
                controller.createHistory(HISTORY_DTO);

        assertEquals(HttpStatus.CREATED, actualResult.getStatusCode());
        assertEquals(HISTORY, actualResult.getBody());
        verify(service).createHistory(HISTORY_DTO);
    }

    @Test
    void updateHistory() {
        ResponseEntity<Void> actualResult =
                controller.updateHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HttpStatus.NO_CONTENT, actualResult.getStatusCode());
        verify(service).updateHistory(HISTORY_ID, HISTORY_DTO);
    }

    @Test
    void updateHistoryThrowHistoryNotFoundException() {
        when(service.updateHistory(HISTORY_ID, HISTORY_DTO))
                .thenThrow(new HistoryNotFoundException());

        HistoryNotFoundException actualResult =
                assertThrows(HistoryNotFoundException.class,
                        () -> controller.updateHistory(HISTORY_ID, HISTORY_DTO));

        assertEquals("История не найдена", actualResult.getReason());
        verify(service).updateHistory(HISTORY_ID, HISTORY_DTO);
    }

    @Test
    void editHistory() {
        ResponseEntity<Void> actualResult =
                controller.editHistory(HISTORY_ID, HISTORY_DTO);

        assertEquals(HttpStatus.NO_CONTENT, actualResult.getStatusCode());
        verify(service).editHistory(HISTORY_ID, HISTORY_DTO);
    }

    @Test
    void deleteHistory() {
        ResponseEntity<Void> actualResult =
                controller.deleteHistory(HISTORY_ID);

        assertEquals(HttpStatus.NO_CONTENT, actualResult.getStatusCode());
        verify(service).deleteHistory(HISTORY_ID);
    }
}
