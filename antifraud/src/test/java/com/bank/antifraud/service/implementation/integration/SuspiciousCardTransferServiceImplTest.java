package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.exception.NotFoundSuspiciousCardTransferException;
import com.bank.antifraud.service.implementation.SuspiciousCardTransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("integration SuspiciousCardTransferServiceImpl tests")
class SuspiciousCardTransferServiceImplTest {

    @Autowired
    private SuspiciousCardTransferServiceImpl service;

    private SuspiciousCardTransferDto dto;
    private SuspiciousCardTransferDto saved;
    private final long WRONG_ID = 500L;

    @BeforeEach
    void setUp() {
        dto = SuspiciousCardTransferDto.builder()
                .cardTransferId(12345L)
                .isSuspicious(false)
                .isBlocked(false)
                .suspiciousReason("test")
                .build();
    }

    @Test
    void add_ShouldAddSuspiciousCardTransferDto() {
        saved = service.add(dto);

        assertNotNull(saved);
        assertDoesNotThrow(() -> service.get(saved.getId()));
    }

    @Test
    void getById_ShouldReturnSuspiciousCardTransferDto() {
        saved = service.add(dto);

        SuspiciousCardTransferDto founded = service.get(saved.getId());

        assertNotNull(founded);
        assertEquals(saved.getId(), founded.getId());
    }

    @Test
    void get_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousCardTransferException.class, () -> service.get(WRONG_ID));
    }

    @Test
    void update_ShouldUpdateSuspiciousCardTransferDto() {
        saved = service.add(dto);
        final SuspiciousCardTransferDto updateDto = SuspiciousCardTransferDto.builder()
                .isSuspicious(true)
                .isBlocked(true)
                .blockedReason("blocked")
                .build();

        final SuspiciousCardTransferDto updated = service.update(saved.getId(), updateDto);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(saved.getCardTransferId(), updated.getCardTransferId());
        assertEquals(updateDto.getIsBlocked(), updated.getIsBlocked());
        assertEquals(updateDto.getIsSuspicious(), updated.getIsSuspicious());
        assertEquals(updateDto.getBlockedReason(), updated.getBlockedReason());
        assertEquals(saved.getSuspiciousReason(), updated.getSuspiciousReason());
    }

    @Test
    void update_ShouldThrowException_WhenNotFound() {
        saved = service.add(dto);

        assertThrows(RuntimeException.class,
                () -> service.update(WRONG_ID, dto),
                "Suspicious card transfer with id " + WRONG_ID + " not found");
    }

    @Test
    void remove_ShouldDeleteSuspiciousCardTransferDto() {
        saved = service.add(dto);

        service.remove(saved.getId());

        assertThrows(NotFoundSuspiciousCardTransferException.class, () -> service.get(saved.getId()));
    }

    @Test
    void remove_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousCardTransferException.class, () -> service.remove(WRONG_ID));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetAllTest")
    void getAll_ParameterizedTest(List<SuspiciousCardTransferDto> list) {
        list.forEach(service::add);

        Page<SuspiciousCardTransferDto> transfer = service.getAll(PageRequest.of(0, 10));

        assertEquals(list.size(), transfer.getTotalElements());
    }

    static Stream<Arguments> getArgumentsForGetAllTest() {
        long id = 1L;
        long cardTransferId = 1L;
        final List<SuspiciousCardTransferDto> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(SuspiciousCardTransferDto.builder()
                    .id(id)
                    .cardTransferId(cardTransferId)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason("test")
                    .build()
            );
            id++;
            cardTransferId++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }
}
