package com.bank.antifraud.service.implementation.unit;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransfer;
import com.bank.antifraud.exception.NotFoundSuspiciousCardTransferException;
import com.bank.antifraud.mapper.SuspiciousCardTransferMapper;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.implementation.SuspiciousCardTransferServiceImpl;
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
@DisplayName("Suspicious card transfer service unit test")
class SuspiciousCardTransferServiceImplTest {

    @Mock
    private SuspiciousCardTransferRepository satRepository;

    @Mock
    private SuspiciousCardTransferMapper satMapper;

    @InjectMocks
    private SuspiciousCardTransferServiceImpl service;

    private static final SuspiciousCardTransferDto DTO = Dto.DEFAULT.getSctDto();
    private static final SuspiciousCardTransfer ENTITY = Entity.DEFAULT.getSct();

    @Test
    void add_ShouldCreateNewTransfer() {
        when(satMapper.toEntity(any(SuspiciousCardTransferDto.class))).thenReturn(ENTITY);
        when(satRepository.save(any(SuspiciousCardTransfer.class))).thenReturn(ENTITY);
        when(satMapper.toDto(any(SuspiciousCardTransfer.class))).thenReturn(DTO);

        SuspiciousCardTransferDto result = service.add(DTO);

        assertNotNull(result);
        assertEquals(DTO, result);
        verify(satRepository).save(any(SuspiciousCardTransfer.class));
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void get_ShouldReturnTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong()))
                    .thenThrow(NotFoundSuspiciousCardTransferException.class);

            assertThrows(NotFoundSuspiciousCardTransferException.class, () -> service.get(id));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satMapper.toDto(any(SuspiciousCardTransfer.class))).thenReturn(DTO);

            SuspiciousCardTransferDto result = service.get(ID);

            assertNotNull(result);
            assertEquals(DTO.getId(), result.getId());
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void update_ShouldUpdateExistingTransfer_Or_ThrowException_WhenNotFound(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.findById(anyLong())).thenThrow(NotFoundSuspiciousCardTransferException.class);

            assertThrows(NotFoundSuspiciousCardTransferException.class,
                    () -> service.update(id, DTO));
        } else {
            when(satRepository.findById(anyLong())).thenReturn(Optional.of(ENTITY));
            when(satRepository.save(any(SuspiciousCardTransfer.class))).thenReturn(ENTITY);
            when(satMapper.toDto(any(SuspiciousCardTransfer.class))).thenReturn(Dto.UPDATED_DTO.getSctDto());

            SuspiciousCardTransferDto result = service.update(id, DTO);

            assertNotNull(result);
            assertEquals(Dto.UPDATED_DTO.getSctDto(), result);
            verify(satMapper).updateExisting(any(SuspiciousCardTransfer.class),
                    any(SuspiciousCardTransferDto.class));
        }
    }

    @ParameterizedTest
    @MethodSource("com.bank.antifraud.TestsRecourse#getId")
    void remove_ShouldDeleteTransfer_Or_ThrowException_WhenNotExists(Long id) {
        if (id == WRONG_ID) {
            when(satRepository.existsById(anyLong())).thenReturn(false);

            assertThrows(NotFoundSuspiciousCardTransferException.class, () -> service.remove(id));
        } else {
            when(satRepository.existsById(ID)).thenReturn(true);

            service.remove(ID);

            verify(satRepository).deleteById(ID);
        }
    }

    @Test
    void getAll_ShouldReturnPageOfTransfers() {
        Page<SuspiciousCardTransfer> page = new PageImpl<>(Collections.singletonList(ENTITY));
        when(satRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(satMapper.toDto(any(SuspiciousCardTransfer.class))).thenReturn(DTO);

        Page<SuspiciousCardTransferDto> result = service.getAll(PAGEABLE);

        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        assertEquals(1, result.getContent().size());
    }
}
