package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.PhoneTransferDto;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static com.bank.antifraud.TestsRecourse.ExpectedResult;
import static com.bank.antifraud.TestsRecourse.ID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Antifraud system phone transfer test")
class PhoneTransferAntifraudImplTest {

    @Mock
    private TransferServiceClient transferService;

    @InjectMocks
    private PhoneTransferAntifraudImpl phoneTransferAntifraud;

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getArgumentsForCheckPhoneTransferTest")
    void checkPhoneTransfer_ShouldUpdateEntity_IfIsSuspicious_Or_IsMustBlocked(
            SuspiciousPhoneTransfers entity,
            List<PhoneTransferDto> previousTransfers,
            PhoneTransferDto transfer,
            ExpectedResult expectedResult) {

        when(transferService.getPhoneTransferById(entity.getPhoneTransferId())).thenReturn(transfer);
        when(transferService.getPhoneTransferByPhoneNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenReturn(previousTransfers);

        SuspiciousPhoneTransfers result = phoneTransferAntifraud.checkPhoneTransfer(entity);

        if (expectedResult == ExpectedResult.NORMAL) {
            assertFalse(result.getIsBlocked());
            assertFalse(result.getIsSuspicious());
            assertNull(result.getBlockedReason());
            assertNull(result.getSuspiciousReason());
        } else if (expectedResult == ExpectedResult.IT_SUSPICIOUS) {
            assertFalse(result.getIsBlocked());
            assertTrue(result.getIsSuspicious());
            assertNull(result.getBlockedReason());
            assertNotNull(result.getSuspiciousReason());
        } else {
            assertTrue(result.getIsBlocked());
            assertTrue(result.getIsSuspicious());
            assertNotNull(result.getBlockedReason());
            assertNotNull(result.getSuspiciousReason());
        }
    }

    @Test
    void getPhoneTransfersByAccountNumberAndAccountDetailsId_WhenNotFound_ThenReturnEmptyList() {
        when(transferService.getPhoneTransferByPhoneNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.NotFound.class);

        List<PhoneTransferDto> result = phoneTransferAntifraud
            .getPhoneTransfersByCardNumberAndAccountDetailsId(ID, ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPhoneTransfersByPhoneNumberAndAccountDetailsId_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getPhoneTransferByPhoneNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            phoneTransferAntifraud.getPhoneTransfersByCardNumberAndAccountDetailsId(ID, ID)
        );
    }

    @Test
    void getPhoneTransferById_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getPhoneTransferById(anyLong())).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            phoneTransferAntifraud.getPhoneTransferById(ID)
        );
    }

}
