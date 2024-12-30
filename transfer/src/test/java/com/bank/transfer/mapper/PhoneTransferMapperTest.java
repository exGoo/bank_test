package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.bank.transfer.ResourcesForTests.phoneTransfer1;
import static com.bank.transfer.ResourcesForTests.phoneTransfer2;
import static com.bank.transfer.ResourcesForTests.phoneTransferDTO1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhoneTransferMapperTest {
    private final PhoneTransferMapper mapper = PhoneTransferMapper.INSTANCE;

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
