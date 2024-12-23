package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import javax.transaction.Transactional;
import java.util.List;
import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("integration SuspiciousAccountTransfersServiceImpl tests")
class SuspiciousAccountTransfersServiceImplTest {

    @Autowired
    private SuspiciousAccountTransfersService service;

    private SuspiciousAccountTransfersDto saved;
    private static final SuspiciousAccountTransfersDto DTO = Dto.DEFAULT.getSatDto();
    private static final SuspiciousAccountTransfersDto DTO_FOR_UPDATE = Dto.FOR_UPDATE.getSatDto();

    @Test
    void add_ShouldAddSuspiciousAccountTransferDto() {
        saved = service.add(DTO);

        assertNotNull(saved);
        assertDoesNotThrow(() -> service.get(saved.getId()));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void getById_ShouldReturnSuspiciousAccountTransferDto(Long id) {
        if (id == WRONG_ID) {
            assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.get(id));
        } else {
            saved = service.add(DTO);

            SuspiciousAccountTransfersDto founded = service.get(saved.getId());

            assertNotNull(founded);
            assertEquals(saved.getId(), founded.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void update_ShouldUpdateSuspiciousAccountTransferDto(Long id) {
        if (id == WRONG_ID) {
            Exception exception = assertThrows(RuntimeException.class, () -> service.update(id, DTO_FOR_UPDATE));
            assertTrue(exception.getMessage().contains("Suspicious account transfers with id " + id + " not found"));
        } else {
            saved = service.add(DTO);

            SuspiciousAccountTransfersDto updated = service.update(saved.getId(), DTO_FOR_UPDATE);

            assertNotNull(updated);
            assertEquals(saved.getId(), updated.getId());
            assertEquals(saved.getAccountTransferId(), updated.getAccountTransferId());
            assertEquals(DTO_FOR_UPDATE.getIsBlocked(), updated.getIsBlocked());
            assertEquals(saved.getIsSuspicious(), updated.getIsSuspicious());
            assertEquals(DTO_FOR_UPDATE.getBlockedReason(), updated.getBlockedReason());
            assertEquals(saved.getSuspiciousReason(), updated.getSuspiciousReason());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void remove_ShouldDeleteSuspiciousAccountTransferDto(Long id) {
        if (id == WRONG_ID) {
            assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.remove(id));
        } else {
            saved = service.add(DTO);

            service.remove(saved.getId());

            assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.get(saved.getId()));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getListOfSuspiciousAccountTransfersDto")
    void getAll_ShouldReturnPageWithSuspiciousAccountTransfersDto(List<SuspiciousAccountTransfersDto> list) {
        list.forEach(service::add);

        Page<SuspiciousAccountTransfersDto> transfers = service.getAll(PAGEABLE);

        assertEquals(list.size(), transfers.getTotalElements());
    }
}
