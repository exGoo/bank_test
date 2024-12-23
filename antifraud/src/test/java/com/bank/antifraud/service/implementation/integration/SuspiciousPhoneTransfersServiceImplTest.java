package com.bank.antifraud.service.implementation.integration;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.exception.NotFoundSuspiciousPhoneTransfersException;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
import static com.bank.antifraud.TestsRecourse.Dto;
import static com.bank.antifraud.TestsRecourse.PAGEABLE;
import static com.bank.antifraud.TestsRecourse.WRONG_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("integration SuspiciousPhoneTransfersServiceImpl tests")
class SuspiciousPhoneTransfersServiceImplTest {

    @Autowired
    private SuspiciousPhoneTransfersService service;

    private SuspiciousPhoneTransfersDto saved;
    private static final SuspiciousPhoneTransfersDto DTO = Dto.DEFAULT.getSptDto();
    private static final SuspiciousPhoneTransfersDto DTO_FOR_UPDATE = Dto.FOR_UPDATE.getSptDto();

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
            assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.get(id));
        } else {
            saved = service.add(DTO);

            SuspiciousPhoneTransfersDto founded = service.get(saved.getId());

            assertNotNull(founded);
            assertEquals(saved.getId(), founded.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void update_ShouldUpdateSuspiciousAccountTransferDto(Long id) {
        if (id == WRONG_ID) {
            Exception exception = assertThrows(RuntimeException.class, () -> service.update(id, DTO_FOR_UPDATE));
            assertTrue(exception.getMessage().contains("Suspicious phone transfers with id " + id + " not found"));
        } else {
            saved = service.add(DTO);

            SuspiciousPhoneTransfersDto updated = service.update(saved.getId(), DTO_FOR_UPDATE);

            assertNotNull(updated);
            assertEquals(saved.getId(), updated.getId());
            assertEquals(saved.getPhoneTransferId(), updated.getPhoneTransferId());
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
            assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.remove(id));
        } else {
            saved = service.add(DTO);

            service.remove(saved.getId());

            assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.get(saved.getId()));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getListOfSuspiciousPhoneTransfersDto")
    void getAll_ShouldReturnPageWithSuspiciousPhoneTransfersDto(List<SuspiciousPhoneTransfersDto> list) {
        list.forEach(service::add);

        Page<SuspiciousPhoneTransfersDto> transfers = service.getAll(PAGEABLE);

        assertEquals(list.size(), transfers.getTotalElements());
    }
}
