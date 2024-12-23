package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhoneTransferMapperTest {
    PhoneTransferDTO phoneTransferDTO1;
    PhoneTransferDTO phoneTransferDTO2;
    PhoneTransfer phoneTransfer1;
    PhoneTransfer phoneTransfer2;
    private final PhoneTransferMapper mapper = PhoneTransferMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        phoneTransferDTO1 = PhoneTransferDTO.builder()
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransferDTO2 = PhoneTransferDTO.builder()
                .phoneNumber(2L)
                .amount(new BigDecimal("394.34"))
                .purpose("purpose2")
                .accountDetailsId(10L)
                .build();
        phoneTransfer1 = PhoneTransfer.builder()
                .phoneNumber(1L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose1")
                .accountDetailsId(5L)
                .build();
        phoneTransfer2 = PhoneTransfer.builder()
                .phoneNumber(2L)
                .amount(new BigDecimal("39654.34"))
                .purpose("purpose2")
                .accountDetailsId(5L)
                .build();
    }

    @Test
    void testPhoneTransferDTOToPhoneTransfer() {
        PhoneTransfer phoneTransfer =
                mapper.phoneTransferDTOToPhoneTransfer(phoneTransferDTO1);
        assertNotNull(phoneTransfer);
        assertEquals(phoneTransferDTO1.getPhoneNumber(), phoneTransfer.getPhoneNumber());
        assertEquals(phoneTransferDTO1.getAmount(), phoneTransfer.getAmount());
        assertEquals(phoneTransferDTO1.getPurpose(), phoneTransfer.getPurpose());
        assertEquals(phoneTransferDTO1.getAccountDetailsId(), phoneTransfer.getAccountDetailsId());
    }

    @Test
    void testPhoneTransferToPhoneTransferDTO() {
        PhoneTransferDTO phoneTransferDTO1 =
                mapper.phoneTransferToPhoneTransferDTO(phoneTransfer1);
        assertNotNull(phoneTransferDTO1);
        assertEquals(phoneTransfer1.getPhoneNumber(), phoneTransferDTO1.getPhoneNumber());
        assertEquals(phoneTransfer1.getAmount(), phoneTransferDTO1.getAmount());
        assertEquals(phoneTransfer1.getPurpose(), phoneTransferDTO1.getPurpose());
        assertEquals(phoneTransfer1.getAccountDetailsId(), phoneTransferDTO1.getAccountDetailsId());
    }

    @Test
    void testPhoneTransferListToDTOList() {
        List<PhoneTransfer> phoneTransferList =
                Arrays.asList(phoneTransfer1, phoneTransfer2);
        List<PhoneTransferDTO> phoneTransferDTOList =
                mapper.phoneTransferListToDTOList(phoneTransferList);
        assertNotNull(phoneTransferDTOList);
        assertEquals(2, phoneTransferDTOList.size());

        PhoneTransferDTO dto1 = phoneTransferDTOList.get(0);
        assertEquals(phoneTransfer1.getId(), dto1.getId());
        assertEquals(phoneTransfer1.getPhoneNumber(), dto1.getPhoneNumber());
        assertEquals(phoneTransfer1.getAmount(), dto1.getAmount());
        assertEquals(phoneTransfer1.getPurpose(), dto1.getPurpose());
        assertEquals(phoneTransfer1.getAccountDetailsId(), dto1.getAccountDetailsId());

        PhoneTransferDTO dto2 = phoneTransferDTOList.get(1);
        assertEquals(phoneTransfer2.getId(), dto2.getId());
        assertEquals(phoneTransfer2.getPhoneNumber(), dto2.getPhoneNumber());
        assertEquals(phoneTransfer2.getAmount(), dto2.getAmount());
        assertEquals(phoneTransfer2.getPurpose(), dto2.getPurpose());
        assertEquals(phoneTransfer2.getAccountDetailsId(), dto2.getAccountDetailsId());
    }

}
