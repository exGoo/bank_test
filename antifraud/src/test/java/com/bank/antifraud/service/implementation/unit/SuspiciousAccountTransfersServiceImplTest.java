package com.bank.antifraud.service.implementation.unit;

import com.bank.antifraud.dto.SuspiciousAccountTransfersDto;
import com.bank.antifraud.entity.SuspiciousAccountTransfers;
import com.bank.antifraud.exception.NotFoundSuspiciousAccountTransfersException;
import com.bank.antifraud.mapper.SuspiciousAccountTransfersMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.service.implementation.SuspiciousAccountTransfersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.bank.antifraud.TestsRecourse.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Suspicious account transfers service unit test")
class SuspiciousAccountTransfersServiceImplTest {

    @Mock
    private SuspiciousAccountTransfersRepository satRepository;

    @Mock
    private SuspiciousAccountTransfersMapper satMapper;

    @InjectMocks
    private SuspiciousAccountTransfersServiceImpl service;

    private static final SuspiciousAccountTransfersDto DTO = Dto.DEFAULT.getSatDto();
    private static final SuspiciousAccountTransfers ENTITY = Entity.DEFAULT.getSat();

    @Test
    void add_ShouldCreateNewTransfer() {
        when(satMapper.toEntity(any(SuspiciousAccountTransfersDto.class))).thenReturn(ENTITY);
        when(satRepository.save(any(SuspiciousAccountTransfers.class))).thenReturn(ENTITY);
        when(satMapper.toDto(any(SuspiciousAccountTransfers.class))).thenReturn(DTO);

        SuspiciousAccountTransfersDto result = service.add(DTO);

        assertNotNull(result);
        assertEquals(DTO, result);
        verify(satRepository).save(any(SuspiciousAccountTransfers.class));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void get_ShouldReturnTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong()))
                    .thenThrow(NotFoundSuspiciousAccountTransfersException.class);

            assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.get(id));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satMapper.toDto(any(SuspiciousAccountTransfers.class))).thenReturn(DTO);

            SuspiciousAccountTransfersDto result = service.get(ID);

            assertNotNull(result);
            assertEquals(DTO.getId(), result.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void update_ShouldUpdateExistingTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong())).thenThrow(NotFoundSuspiciousAccountTransfersException.class);

            assertThrows(NotFoundSuspiciousAccountTransfersException.class,
                    () -> service.update(id, DTO));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satRepository.save(any(SuspiciousAccountTransfers.class))).thenReturn(ENTITY);
            when(satMapper.toDto(any(SuspiciousAccountTransfers.class))).thenReturn(Dto.UPDATED_DTO.getSatDto());

            SuspiciousAccountTransfersDto result = service.update(id, DTO);

            assertNotNull(result);
            assertEquals(Dto.UPDATED_DTO.getSatDto(), result);
            verify(satMapper).updateExisting(any(SuspiciousAccountTransfers.class),
                    any(SuspiciousAccountTransfersDto.class));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void remove_ShouldDeleteTransfer_Or_ThrowException_WhenNotExists(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.existsById(anyLong())).thenReturn(false);

            assertThrows(NotFoundSuspiciousAccountTransfersException.class, () -> service.remove(id));
        } else {
            when(satRepository.existsById(ID)).thenReturn(true);

            service.remove(ID);

            verify(satRepository).deleteById(ID);
        }
    }

    @Test
    void getAll_ShouldReturnPageOfTransfers() {
        Page<SuspiciousAccountTransfers> page = new PageImpl<>(Collections.singletonList(ENTITY));
        when(satRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(satMapper.toDto(any(SuspiciousAccountTransfers.class))).thenReturn(DTO);

        Page<SuspiciousAccountTransfersDto> result = service.getAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
    }
}
