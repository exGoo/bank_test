package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.ACCOUNT_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.accountTransfer1;
import static com.bank.transfer.ResourcesForTests.accountTransfer2;
import static com.bank.transfer.ResourcesForTests.accountTransferDTO1;
import static com.bank.transfer.ResourcesForTests.accountTransferDTO2;
import static com.bank.transfer.ResourcesForTests.accountTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransferServiceImplTest {

    @Mock
    private AccountTransferRepository accountTransferRepository;
    @Mock
    private AccountTransferMapper mapper;
    @InjectMocks
    private AccountTransferServiceImpl accountTransferService;

    @Test
    void getAccountTransferById() {
        when(accountTransferRepository.findById(ID_1)).thenReturn(Optional.of(accountTransfer1));
        when(mapper.accountTransferToAccountTransferDTO(accountTransfer1)).thenReturn(accountTransferDTO1);
        Optional<AccountTransferDTO> optionalAccountTransferDTO = accountTransferService.getAccountTransferById(ID_1);
        AccountTransferDTO accountTransferDTO = optionalAccountTransferDTO.get();
        assertNotNull(accountTransferDTO);
        assertEquals(accountTransferDTO1, accountTransferDTO);
    }

    @Test
    void allAccountTransfer() {
        when(accountTransferRepository.findAll())
                .thenReturn(List.of(accountTransfer1, accountTransfer2));
        when(mapper.accountTransferListToDTOList(List.of(accountTransfer1, accountTransfer2)))
                .thenReturn(List.of(accountTransferDTO1, accountTransferDTO2));
        List<AccountTransferDTO> allAccountTransfersDTO = accountTransferService.allAccountTransfer();
        assertEquals(allAccountTransfersDTO, accountTransferListDTO);
    }

    @Test
    void saveAccountTransfer() {

        when(mapper.accountTransferDTOToAccountTransfer(accountTransferDTO1)).thenReturn(accountTransfer1);
        when(accountTransferRepository.save(accountTransfer1)).thenReturn(accountTransfer1);
        AccountTransfer savedAccountTransfer = accountTransferService.saveAccountTransfer(accountTransferDTO1);

        assertNotNull(savedAccountTransfer);
        assertEquals(accountTransfer1, savedAccountTransfer);
    }

    @Test
    void testUpdateAccountTransferById() {

        // Настройка моков
        when(accountTransferRepository.findById(ID_1)).thenReturn(Optional.of(accountTransfer1));
        when(accountTransferRepository.save(any(AccountTransfer.class))).thenReturn(accountTransfer1);

        // Вызов метода
        AccountTransfer updatedAccountTransfer = accountTransferService.updateAccountTransferById(accountTransferDTO2, ID_1);

        // Проверки
        assertNotNull(updatedAccountTransfer);
        assertEquals(ACCOUNT_NUMBER_2, updatedAccountTransfer.getAccountNumber());
        assertEquals(AMOUNT_2, updatedAccountTransfer.getAmount());
        assertEquals(PURPOSE_2, updatedAccountTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_2, updatedAccountTransfer.getAccountDetailsId());
        // Проверка вызовов методов
        verify(accountTransferRepository).findById(ID_1);
        verify(accountTransferRepository).save(accountTransfer1);
    }

    @Test
    void updateAccountTransferById_ShouldThrowException_WhenAccountTransferNotFound() {
        // Настройка поведения мока
        when(accountTransferRepository.findById(ID_1)).thenReturn(Optional.empty());

        // Проверка на отсутствие сущности
        assertThrows(EntityNotFoundException.class, () -> {
            accountTransferService.updateAccountTransferById(accountTransferDTO1, ID_1);
        });
    }

    @Test
    void deleteAccountTransfer() {
        accountTransferService.deleteAccountTransfer(ID_1);
        verify(accountTransferRepository).deleteById(ID_1);
    }
}
