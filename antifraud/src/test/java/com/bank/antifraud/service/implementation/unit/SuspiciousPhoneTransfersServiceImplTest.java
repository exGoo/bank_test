package com.bank.antifraud.service.implementation.unit;

import com.bank.antifraud.dto.SuspiciousPhoneTransfersDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransfers;
import com.bank.antifraud.exception.NotFoundSuspiciousPhoneTransfersException;
import com.bank.antifraud.mapper.SuspiciousPhoneTransfersMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.service.implementation.SuspiciousPhoneTransfersServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static com.bank.antifraud.TestsRecourse.Dto;
import static com.bank.antifraud.TestsRecourse.Entity;
import static com.bank.antifraud.TestsRecourse.ID;
import static com.bank.antifraud.TestsRecourse.PAGEABLE;
import static com.bank.antifraud.TestsRecourse.WRONG_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Suspicious phone transfers service unit test")
class SuspiciousPhoneTransfersServiceImplTest {

    @Mock
    private SuspiciousPhoneTransfersRepository satRepository;

    @Mock
    private SuspiciousPhoneTransfersMapper satMapper;

    @InjectMocks
    private SuspiciousPhoneTransfersServiceImpl service;

    private static final SuspiciousPhoneTransfersDto DTO = Dto.DEFAULT.getSptDto();
    private static final SuspiciousPhoneTransfers ENTITY = Entity.DEFAULT.getSpt();

    @Test
    void add_ShouldCreateNewTransfer() {
        when(satMapper.toEntity(any(SuspiciousPhoneTransfersDto.class))).thenReturn(ENTITY);
        when(satRepository.save(any(SuspiciousPhoneTransfers.class))).thenReturn(ENTITY);
        when(satMapper.toDto(any(SuspiciousPhoneTransfers.class))).thenReturn(DTO);

        SuspiciousPhoneTransfersDto result = service.add(DTO);

        assertNotNull(result);
        assertEquals(DTO, result);
        verify(satRepository).save(any(SuspiciousPhoneTransfers.class));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void get_ShouldReturnTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong()))
                    .thenThrow(NotFoundSuspiciousPhoneTransfersException.class);

            assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.get(id));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satMapper.toDto(any(SuspiciousPhoneTransfers.class))).thenReturn(DTO);

            SuspiciousPhoneTransfersDto result = service.get(ID);

            assertNotNull(result);
            assertEquals(DTO.getId(), result.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void update_ShouldUpdateExistingTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong())).thenThrow(NotFoundSuspiciousPhoneTransfersException.class);

            assertThrows(NotFoundSuspiciousPhoneTransfersException.class,
                    () -> service.update(id, DTO));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satRepository.save(any(SuspiciousPhoneTransfers.class))).thenReturn(ENTITY);
            when(satMapper.toDto(any(SuspiciousPhoneTransfers.class))).thenReturn(Dto.UPDATED_DTO.getSptDto());

            SuspiciousPhoneTransfersDto result = service.update(id, DTO);

            assertNotNull(result);
            assertEquals(Dto.UPDATED_DTO.getSptDto(), result);
            verify(satMapper).updateExisting(any(SuspiciousPhoneTransfers.class),
                    any(SuspiciousPhoneTransfersDto.class));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void remove_ShouldDeleteTransfer_Or_ThrowException_WhenNotExists(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.existsById(anyLong())).thenReturn(false);

            assertThrows(NotFoundSuspiciousPhoneTransfersException.class, () -> service.remove(id));
        } else {
            when(satRepository.existsById(ID)).thenReturn(true);

            service.remove(ID);

            verify(satRepository).deleteById(ID);
        }
    }

    @Test
    void getAll_ShouldReturnPageOfTransfers() {
        Page<SuspiciousPhoneTransfers> page = new PageImpl<>(Collections.singletonList(ENTITY));
        when(satRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(satMapper.toDto(any(SuspiciousPhoneTransfers.class))).thenReturn(DTO);

        Page<SuspiciousPhoneTransfersDto> result = service.getAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
    }
}
