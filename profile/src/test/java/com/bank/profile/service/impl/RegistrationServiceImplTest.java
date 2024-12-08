package com.bank.profile.service.impl;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.dto.mapper.RegistrationMapper;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.RegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    Registration ENTITY = Registration.builder()
            .id(1L)
            .build();
    RegistrationDto DTO = RegistrationDto.builder()
            .id(1L)
            .build();

    @Mock
    RegistrationRepository repository;
    @Mock
    RegistrationMapper mapper;
    @InjectMocks
    RegistrationServiceImpl service;

    @Test
    void save() {
        when(repository.save(any(Registration.class))).thenReturn(ENTITY);
        when(mapper.toEntity(any(RegistrationDto.class))).thenReturn(ENTITY);
        when(mapper.toDto(any(Registration.class))).thenReturn(DTO);

        RegistrationDto result = service.save(DTO);

        assertNotNull(result);
        assertEquals(result.getId(), DTO.getId());
    }

    @Test
    void findAll() {
        List<RegistrationDto> dtos = List.of(DTO);
        when(repository.findAll()).thenReturn(List.of(ENTITY));
        when(mapper.toListDto(any())).thenReturn(dtos);

        List<RegistrationDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(result, dtos);

    }

    @Test
    void givenValidId_whenFindByIdCalled_thenReturnsDto() {
        when(mapper.toDto(any(Registration.class))).thenReturn(DTO);
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(ENTITY));

        RegistrationDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(result.getId(), DTO.getId());
    }

    @Test
    void givenInvalidId_whenFindByIdCalled_thenThrowException() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findById(1L)
        );

        assertEquals("registration not found with ID: 1", result.getMessage());
    }

    @Test
    void givenValidData_whenUpdateCalled_thenReturnsDto() {
        //сущность есть в БД и загружена
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(ENTITY));
        doNothing().when(mapper).updateEntityFromDto(ENTITY, DTO);
        when(repository.save(any(Registration.class))).thenReturn(ENTITY);
        when(mapper.toDto(any(Registration.class))).thenReturn(DTO);

        RegistrationDto result = service.update(1L, DTO);

        assertNotNull(result);
        assertEquals(DTO.getId(), result.getId());
    }

    @Test
    void givenInvalidId_whenUpdateCalled_thenThrowException() {
        //сущность отсутствует в БД
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.update(999L, DTO)
        );

        assertNotNull(result);
        assertEquals("registration not found with ID: 999", result.getMessage());
    }

    @Test
    void givenValidId_whenDeleteCalled_thenEntityDeleted() {
        doNothing().when(repository).deleteById(any(Long.class));

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidId_whenDeleteCalled_thenThrowException() {
        doThrow(new EntityNotFoundException()).when(repository).deleteById(any(Long.class));

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class
                , () -> service.deleteById(999L)
        );
        assertEquals(null, result.getMessage());
    }
}
