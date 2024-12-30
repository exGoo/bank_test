package com.bank.transfer.serviceImpl;

import com.bank.transfer.dto.PhoneTransferDTO;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.impl.PhoneTransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.bank.transfer.ResourcesForTests.ACCOUNT_DETAILS_ID_2;
import static com.bank.transfer.ResourcesForTests.AMOUNT_2;
import static com.bank.transfer.ResourcesForTests.ID_1;
import static com.bank.transfer.ResourcesForTests.PHONE_NUMBER_2;
import static com.bank.transfer.ResourcesForTests.PURPOSE_2;
import static com.bank.transfer.ResourcesForTests.phoneTransfer1;
import static com.bank.transfer.ResourcesForTests.phoneTransfer2;
import static com.bank.transfer.ResourcesForTests.phoneTransferDTO1;
import static com.bank.transfer.ResourcesForTests.phoneTransferDTO2;
import static com.bank.transfer.ResourcesForTests.phoneTransferListDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneTransferServiceImplTest {

    @Mock
    private PhoneTransferRepository phoneTransferRepository;
    @Mock
    private PhoneTransferMapper mapper;
    @InjectMocks
    private PhoneTransferServiceImpl phoneTransferService;

    @Test
    void getPhoneTransferById() {
        when(phoneTransferRepository.findById(1L)).thenReturn(Optional.of(phoneTransfer1));
        when(mapper.phoneTransferToPhoneTransferDTO(phoneTransfer1)).thenReturn(phoneTransferDTO1);
        Optional<PhoneTransferDTO> optionalPhoneTransferDTO = phoneTransferService.getPhoneTransferById(ID_1);
        PhoneTransferDTO phoneTransferDTO = optionalPhoneTransferDTO.get();
        assertNotNull(phoneTransferDTO);
        assertEquals(phoneTransferDTO1, phoneTransferDTO);
    }

    @Test
    void allPhoneTransfer() {
        when(phoneTransferRepository.findAll())
                .thenReturn(List.of(phoneTransfer1, phoneTransfer2));
        when(mapper.phoneTransferListToDTOList(List.of(phoneTransfer1, phoneTransfer2)))
                .thenReturn(List.of(phoneTransferDTO1, phoneTransferDTO2));
        List<PhoneTransferDTO> allPhoneTransfersDTO = phoneTransferService.allPhoneTransfer();
        assertEquals(allPhoneTransfersDTO, phoneTransferListDTO);
    }

    @Test
    void savePhoneTransfer() {

        when(mapper.phoneTransferDTOToPhoneTransfer(phoneTransferDTO1)).thenReturn(phoneTransfer1);
        when(phoneTransferRepository.save(phoneTransfer1)).thenReturn(phoneTransfer1);
        PhoneTransfer savedPhoneTransfer = phoneTransferService.savePhoneTransfer(phoneTransferDTO1);

        assertNotNull(savedPhoneTransfer);
        assertEquals(phoneTransfer1, savedPhoneTransfer);
    }

    @Test
    void testUpdatePhoneTransferById() {

        when(phoneTransferRepository.findById(ID_1)).thenReturn(Optional.of(phoneTransfer1));
        when(phoneTransferRepository.save(any(PhoneTransfer.class))).thenReturn(phoneTransfer1);

        PhoneTransfer updatedPhoneTransfer = phoneTransferService.updatePhoneTransferById(phoneTransferDTO2, ID_1);

        assertNotNull(updatedPhoneTransfer);
        assertEquals(PHONE_NUMBER_2, updatedPhoneTransfer.getPhoneNumber());
        assertEquals(AMOUNT_2, updatedPhoneTransfer.getAmount());
        assertEquals(PURPOSE_2, updatedPhoneTransfer.getPurpose());
        assertEquals(ACCOUNT_DETAILS_ID_2, updatedPhoneTransfer.getAccountDetailsId());
        verify(phoneTransferRepository).findById(ID_1);
        verify(phoneTransferRepository).save(phoneTransfer1);
    }

    @Test
    void updatePhoneTransferById_ShouldThrowException_WhenPhoneTransferNotFound() {

        when(phoneTransferRepository.findById(ID_1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            phoneTransferService.updatePhoneTransferById(phoneTransferDTO1, ID_1);
        });
    }

    @Test
    void deletePhoneTransfer() {
        phoneTransferService.deletePhoneTransfer(ID_1);
        verify(phoneTransferRepository).deleteById(ID_1);
    }
}
