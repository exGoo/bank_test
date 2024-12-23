package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.AccountTransferDto;
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
import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Antifraud system account transfer test")
class AccountTransferAntifraudImplTest {

    @Mock
    private TransferServiceClient transferService;

    @InjectMocks
    private AccountTransferAntifraudImpl accountTransferAntifraud;

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getArgumentsForCheckAccountTransferTest")
    void checkAccountTransfer_ShouldUpdateEntity_IfIsSuspicious_Or_IsMustBlocked(
            SuspiciousAccountTransfers entity,
            List<AccountTransferDto> previousTransfers,
            AccountTransferDto transfer,
            ExpectedResult expectedResult) {

        when(transferService.getAccountTransferById(entity.getAccountTransferId())).thenReturn(transfer);
        when(transferService.getAccountTransferByAccountNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenReturn(previousTransfers);

        SuspiciousAccountTransfers result = accountTransferAntifraud.checkAccountTransfer(entity);

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
    void getAccountTransfersByAccountNumberAndAccountDetailsId_WhenNotFound_ThenReturnEmptyList() {
        when(transferService.getAccountTransferByAccountNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.NotFound.class);

        List<AccountTransferDto> result = accountTransferAntifraud
            .getAccountTransfersByAccountNumberAndAccountDetailsId(ID, ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAccountTransfersByAccountNumberAndAccountDetailsId_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getAccountTransferByAccountNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            accountTransferAntifraud.getAccountTransfersByAccountNumberAndAccountDetailsId(ID, ID)
        );
    }

    @Test
    void getAccountTransferById_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getAccountTransferById(anyLong())).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            accountTransferAntifraud.getAccountTransferById(ID)
        );
    }

}
