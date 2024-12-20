package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousPhoneTransfersException;
import com.bank.antifraud.service.implementation.SuspiciousPhoneTransfersServiceImpl;
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
@DisplayName("integration SuspiciousPhoneTransfersServiceImpl tests")
class SuspiciousPhoneTransfersServiceImplTest {

    @Autowired
    private SuspiciousPhoneTransfersServiceImpl service;

    private SuspiciousPhoneTransfersDto dto;
    private SuspiciousPhoneTransfersDto saved;
    private final long WRONG_ID = 500L;

    @BeforeEach
    void setUp() {
        dto = SuspiciousPhoneTransfersDto.builder()
                .phoneTransferId(12345L)
                .isSuspicious(false)
                .isBlocked(false)
                .suspiciousReason("test")
                .build();
    }

    @Test
    void add_ShouldAddSuspiciousPhoneTransferDto() {
        saved = service.add(dto);

        assertNotNull(saved);
        assertDoesNotThrow(() -> service.get(saved.getId()));
    }

    @Test
    void getById_ShouldReturnSuspiciousPhoneTransferDto() {
        saved = service.add(dto);

        SuspiciousPhoneTransfersDto founded = service.get(saved.getId());

        assertNotNull(founded);
        assertEquals(saved.getId(), founded.getId());
    }

    @Test
    void get_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.get(WRONG_ID));
    }

    @Test
    void update_ShouldUpdateSuspiciousPhoneTransferDto() {
        saved = service.add(dto);
        final SuspiciousPhoneTransfersDto updateDto = SuspiciousPhoneTransfersDto.builder()
                .isSuspicious(true)
                .isBlocked(true)
                .blockedReason("blocked")
                .build();

        final SuspiciousPhoneTransfersDto updated = service.update(saved.getId(), updateDto);

        assertNotNull(updated);
        assertEquals(saved.getId(), updated.getId());
        assertEquals(saved.getPhoneTransferId(), updated.getPhoneTransferId());
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
                "Suspicious phone transfers with id " + WRONG_ID + " not found");
    }

    @Test
    void remove_ShouldDeleteSuspiciousPhoneTransferDto() {
        saved = service.add(dto);

        service.remove(saved.getId());

        assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.get(saved.getId()));
    }

    @Test
    void remove_ShouldThrowException_WhenNotFound() {
        assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.remove(WRONG_ID));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForGetAllTest")
    void getAll_ParameterizedTest(List<SuspiciousPhoneTransfersDto> list) {
        list.forEach(service::add);

        Page<SuspiciousPhoneTransfersDto> transfers = service.getAll(PageRequest.of(0, 10));

        assertEquals(list.size(), transfers.getTotalElements());
    }

    static Stream<Arguments> getArgumentsForGetAllTest() {
        long id = 1L;
        long phoneTransferId = 1L;
        final List<SuspiciousPhoneTransfersDto> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(SuspiciousPhoneTransfersDto.builder()
                    .id(id)
                    .phoneTransferId(phoneTransferId)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .suspiciousReason("test")
                    .build()
            );
            id++;
            phoneTransferId++;
        }
        return Stream.of(
                Arguments.of(list),
                Arguments.of(List.of())
        );
    }
}
