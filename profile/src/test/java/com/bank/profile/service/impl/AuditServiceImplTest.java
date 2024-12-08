package com.bank.profile.service.impl;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    static AuditRepository repository;
    @Mock
    static AuditMapper mapper;
    @InjectMocks
    static AuditServiceImpl service;
    AuditDto DTO = AuditDto.builder()
            .id(1L)
            .build();
    Audit ENTITY = Audit.builder()
            .id(1L)
            .build();

    @Test
    void save() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AuditDto result = service.save(DTO);
        assertEquals(DTO, result);
        verify(repository, times(1)).save(ENTITY);
        verify(mapper, times(1)).toEntity(DTO);
    }

    @Test
    void givenInvalidData_whenSave_thenThrowException() {
        when(mapper.toEntity(DTO))
                .thenThrow(new RuntimeException("Ошибка при создании audit:"));

        RuntimeException result = assertThrows(
                RuntimeException.class
                , () -> service.save(DTO)
        );
        assertEquals("Ошибка при создании audit:", result.getMessage());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));
        when(mapper.toListDto(List.of(ENTITY))).thenReturn(List.of(DTO));

        List<AuditDto> result = service.findAll();

        assertEquals(List.of(DTO), result);
    }

    @Test
    void givenInvalidData_whenFindAll_thenThrowException() {
        when(repository.findAll())
                .thenThrow(new EntityNotFoundException("Ошибка при получении списка audit записей"));

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findAll()
        );

        assertEquals("Ошибка при получении списка audit записей", result.getMessage());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AuditDto result = service.findById(1L);

        assertEquals(DTO, result);
    }

    @Test
    void givenInvalidDate_whenFindById_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findById(1L)
        );

        assertEquals("Audit not found with ID: 1", result.getMessage());
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        doNothing().when(mapper).updateEntityFromDto(ENTITY, DTO);
        when(repository.save(ENTITY)).thenReturn(ENTITY);

        service.update(1L, DTO);

        verify(repository, times(1)).save(ENTITY);
        verify(mapper, times(1)).updateEntityFromDto(ENTITY, DTO);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void givenInvalidDate_whenUpdate_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.update(1L, DTO)
        );

        assertEquals("audit not found with ID: 1", result.getMessage());
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidData_whenDeleteById_thenThrowException() {
        doThrow(new EntityNotFoundException("Ошибка при удалении audit"))
                .when(repository).deleteById(1L);

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.deleteById(1L)
        );
        assertEquals("Ошибка при удалении audit", result.getMessage());
    }
}
