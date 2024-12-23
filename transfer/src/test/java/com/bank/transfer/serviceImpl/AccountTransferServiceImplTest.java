package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.impl.AccountTransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransferServiceImplTest {
    public static final Long ACCOUNT_TRANSFER_ID = 1L;

    @Mock
    private AccountTransferRepository accountTransferRepository;
    @Mock
    private AccountTransferMapper mapper;
    @InjectMocks
    private AccountTransferServiceImpl accountTransferService;

    AccountTransferDTO accountTransferDTO1;
    AccountTransferDTO accountTransferDTO2;
    AccountTransfer accountTransfer1;
    AccountTransfer accountTransfer2;
    List<AccountTransfer> accountTransferList = new ArrayList<>();
    List<AccountTransferDTO> accountTransferListDTO = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        accountTransfer1 = AccountTransfer.builder()
                .id(1L)
                .accountNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        accountTransfer2 = AccountTransfer.builder()
                .id(2L)
                .accountNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();
        accountTransferDTO1 = AccountTransferDTO.builder()
                .id(1L)
                .accountNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        accountTransferDTO2 = AccountTransferDTO.builder()
                .id(2L)
                .accountNumber(2L)
                .amount(new BigDecimal("34.34"))
                .purpose("purpose2")
                .accountDetailsId(6L)
                .build();

        accountTransferList.add(accountTransfer1);
        accountTransferList.add(accountTransfer2);

        accountTransferListDTO.add(accountTransferDTO1);
        accountTransferListDTO.add(accountTransferDTO2);
    }

    @Test
    void getAccountTransferById() {
        when(accountTransferRepository.findById(1L)).thenReturn(Optional.of(accountTransfer1));
        when(mapper.accountTransferToAccountTransferDTO(accountTransfer1)).thenReturn(accountTransferDTO1);
        Optional<AccountTransferDTO> optionalAccountTransferDTO = accountTransferService.getAccountTransferById(1L);
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

//    @Test
//    void testUpdateAccountTransferById() {
//
//        when(accountTransferService.getAccountTransferById(2L)).thenReturn(Optional.of(accountTransferDTO2));
//
//        // Настройка мока для поиска AccountTransfer в репозитории
//        when(accountTransferRepository.findById(2L)).thenReturn(Optional.of(accountTransfer2));
//
//        // Настройка мока для преобразования DTO в Entity
//        when(mapper.accountTransferDTOToAccountTransfer(accountTransferDTO2)).thenReturn(accountTransfer2);
//
//        // Настройка мока для сохранения в репозитории
//        when(accountTransferRepository.save(any(AccountTransfer.class))).thenReturn(accountTransfer2);
//
//        // Вызов метода
//        AccountTransfer updatedAccountTransfer = accountTransferService.updateAccountTransferById(accountTransferDTO2, 2L);
//
//        // Проверки
//        assertNotNull(updatedAccountTransfer);
//        assertEquals(2L, updatedAccountTransfer.getAccountNumber());
//        assertEquals(new BigDecimal("34.34"), updatedAccountTransfer.getAmount());
//        assertEquals("purpose2", updatedAccountTransfer.getPurpose());
//        assertEquals(6L, updatedAccountTransfer.getAccountDetailsId());
//
//        // Проверка вызовов методов
//        verify(accountTransferRepository).findById(2L);
//        verify(accountTransferService).getAccountTransferById(2L); // Проверка вызова getAccountTransferById
//        verify(mapper).accountTransferDTOToAccountTransfer(accountTransferDTO2);
//        verify(accountTransferRepository).save(accountTransfer2);
//    }
    @Test
    void updateAccountTransferById_ShouldThrowException_WhenAccountTransferDTOIsNull() {
        // Проверка на null
        assertThrows(IllegalArgumentException.class, () -> {
            accountTransferService.updateAccountTransferById(null, 1L);
        });
    }
    @Test
    void updateAccountTransferById_ShouldThrowException_WhenAccountTransferNotFound() {
        // Настройка поведения мока
        when(accountTransferRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка на отсутствие сущности
        assertThrows(EntityNotFoundException.class, () -> {
            accountTransferService.updateAccountTransferById(accountTransferDTO1, 1L);
        });
    }
    @Test
    void deleteAccountTransfer() {
        accountTransferService.deleteAccountTransfer(ACCOUNT_TRANSFER_ID);
        verify(accountTransferRepository).deleteById(ACCOUNT_TRANSFER_ID);
    }
}
