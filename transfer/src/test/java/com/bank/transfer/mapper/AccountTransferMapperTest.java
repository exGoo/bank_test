package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDTO;
import com.bank.transfer.entity.AccountTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTransferMapperTest {
    AccountTransferDTO accountTransferDTO1;
    AccountTransferDTO accountTransferDTO2;
    AccountTransfer accountTransfer1;
    AccountTransfer accountTransfer2;
    private final AccountTransferMapper mapper = AccountTransferMapper.INSTANCE;

    @BeforeEach
    void setUp() {
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
                .amount(new BigDecimal("394.34"))
                .purpose("purpose2")
                .accountDetailsId(10L)
                .build();
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
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose2")
                .accountDetailsId(5L)
                .build();
    }

    @Test
    void testAccountTransferDTOToAccountTransfer() {
        AccountTransfer accountTransfer =
                mapper.accountTransferDTOToAccountTransfer(accountTransferDTO1);
        assertNotNull(accountTransfer);
        assertEquals(accountTransferDTO1.getId(), accountTransfer.getId());
        assertEquals(accountTransferDTO1.getAccountNumber(), accountTransfer.getAccountNumber());
        assertEquals(accountTransferDTO1.getAmount(), accountTransfer.getAmount());
        assertEquals(accountTransferDTO1.getPurpose(), accountTransfer.getPurpose());
        assertEquals(accountTransferDTO1.getAccountDetailsId(), accountTransfer.getAccountDetailsId());
    }

    @Test
    void testAccountTransferToAccountTransferDTO() {
        AccountTransferDTO accountTransferDTO1 =
                mapper.accountTransferToAccountTransferDTO(accountTransfer1);
        assertNotNull(accountTransferDTO1);
        assertEquals(accountTransfer1.getAccountNumber(), accountTransferDTO1.getAccountNumber());
        assertEquals(accountTransfer1.getAmount(), accountTransferDTO1.getAmount());
        assertEquals(accountTransfer1.getPurpose(), accountTransferDTO1.getPurpose());
        assertEquals(accountTransfer1.getAccountDetailsId(), accountTransferDTO1.getAccountDetailsId());
    }
    @Test
    void testAccountTransferListToDTOList(){
        List<AccountTransfer> accountTransferList =
                Arrays.asList(accountTransfer1,accountTransfer2);
        List<AccountTransferDTO> accountTransferDTOList =
                mapper.accountTransferListToDTOList(accountTransferList);
        assertNotNull(accountTransferDTOList);
        assertEquals(2, accountTransferDTOList.size(), "Size of DTO list should match the size of AccountTransfer list");

        // Проверяем, что поля были корректно скопированы
        AccountTransferDTO dto1 = accountTransferDTOList.get(0);
        assertEquals(accountTransfer1.getId(), dto1.getId());
        assertEquals(accountTransfer1.getAccountNumber(), dto1.getAccountNumber());
        assertEquals(accountTransfer1.getAmount(), dto1.getAmount());
        assertEquals(accountTransfer1.getPurpose(), dto1.getPurpose());
        assertEquals(accountTransfer1.getAccountDetailsId(), dto1.getAccountDetailsId());

        AccountTransferDTO dto2 = accountTransferDTOList.get(1);
        assertEquals(accountTransfer2.getId(), dto2.getId());
        assertEquals(accountTransfer2.getAccountNumber(), dto2.getAccountNumber());
        assertEquals(accountTransfer2.getAmount(), dto2.getAmount());
        assertEquals(accountTransfer2.getPurpose(), dto2.getPurpose());
        assertEquals(accountTransfer2.getAccountDetailsId(), dto2.getAccountDetailsId());
    }

}