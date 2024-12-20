package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("integration SuspiciousAccountTransfersServiceImpl tests")
class SuspiciousAccountTransfersServiceImplTest {

    @Autowired
    private SuspiciousAccountTransfersService service;

    private SuspiciousAccountTransfersDto dto;
    private SuspiciousAccountTransfersDto saved;
    private final long WRONG_ID = 500L;

    @BeforeEach
    void setUp() {
        dto = SuspiciousAccountTransfersDto.builder()
                .accountTransferId(12345L)
                .isSuspicious(false)
                .isBlocked(false)
                .suspiciousReason("test")
                .build();
    }

    @Test
    void add_ShouldAddSuspiciousAccountTransferDto() {
        saved = service.add(dto);

        assertNotNull(saved);
        assertDoesNotThrow(() -> service.get(saved.getId()));
    }

    @Test
    void getById_ShouldReturnSuspiciousAccountTransferDto() {
        saved = service.add(dto);

        SuspiciousAccountTransfersDto founded = service.get(saved.getId());

        assertNotNull(founded);
        assertEquals(saved.getId(), founded.getId());
    }

    @Test
    void get_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.get(WRONG_ID));
    }

    @Test
    void update_ShouldUpdateSuspiciousAccountTransferDto() {
        saved = service.add(dto);
        final SuspiciousAccountTransfersDto updateDto = SuspiciousAccountTransfersDto.builder()
                .isSuspicious(true)
                .isBlocked(true)
                .blockedReason("blocked")
                .build();

        final SuspiciousAccountTransfersDto updated = service.update(saved.getId(), updateDto);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(saved.getAccountTransferId(), updated.getAccountTransferId());
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
                "Suspicious account transfers with id " + WRONG_ID + " not found");
    }

    @Test
    void remove_ShouldDeleteSuspiciousAccountTransferDto() {
        saved = service.add(dto);

        service.remove(saved.getId());

        assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.get(saved.getId()));
    }

    @Test
    void remove_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.remove(WRONG_ID));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetAllTest")
    void getAll_ParametrizedTest(List<SuspiciousAccountTransfersDto> list) {
        list.forEach(service::add);

        Page<SuspiciousAccountTransfersDto> transfers = service.getAll(PageRequest.of(0, 10));

        assertEquals(list.size(), transfers.getTotalElements());
    }

    static Stream<Arguments> getArgumentsForGetAllTest() {
        long id = 1L;
        long accountTransferId = 1L;
        final List<SuspiciousAccountTransfersDto> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(SuspiciousAccountTransfersDto.builder()
                    .id(id)
                    .accountTransferId(accountTransferId)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason("test")
                    .build()
            );
            id++;
            accountTransferId++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }
}
