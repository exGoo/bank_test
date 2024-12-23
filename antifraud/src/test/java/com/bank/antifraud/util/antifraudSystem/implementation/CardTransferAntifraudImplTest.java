package com.bank.antifraud.util.antifraudSystem.implementation;

import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.util.antifraudSystem.TransferServiceClient;
import com.bank.antifraud.util.antifraudSystem.transferDto.CardTransferDto;
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
@DisplayName("Antifraud system card transfer test")
class CardTransferAntifraudImplTest {

    @Mock
    private TransferServiceClient transferService;

    @InjectMocks
    private CardTransferAntifraudImpl CardTransferAntifraud;

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getArgumentsForCheckCardTransferTest")
    void checkCardTransfer_ShouldUpdateEntity_IfIsSuspicious_Or_IsMustBlocked(
            SuspiciousCardTransfer entity,
            List<CardTransferDto> previousTransfers,
            CardTransferDto transfer,
            ExpectedResult expectedResult) {

        when(transferService.getCardTransferById(entity.getCardTransferId())).thenReturn(transfer);
        when(transferService.getCardTransferByCardNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenReturn(previousTransfers);

        SuspiciousCardTransfer result = CardTransferAntifraud.checkCardTransfer(entity);

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
    void getCardTransfersByCardNumberAndCardDetailsId_WhenNotFound_ThenReturnEmptyList() {
        when(transferService.getCardTransferByCardNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.NotFound.class);

        List<CardTransferDto> result = CardTransferAntifraud
            .getCardTransfersByCardNumberAndAccountDetailsId(ID, ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void getCardTransfersByCardNumberAndCardDetailsId_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getCardTransferByCardNumberAndAccountDetailsId(
            anyLong(), anyLong()
        )).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            CardTransferAntifraud.getCardTransfersByCardNumberAndAccountDetailsId(ID, ID)
        );
    }

    @Test
    void getCardTransferById_WhenFeignError_ThenThrowRuntime() {
        when(transferService.getCardTransferById(anyLong())).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () ->
            CardTransferAntifraud.getCardTransferById(ID)
        );
    }

}
