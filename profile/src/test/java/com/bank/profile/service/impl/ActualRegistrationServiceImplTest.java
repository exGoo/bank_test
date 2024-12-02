package com.bank.profile.service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.mapper.ActualRegistrationMapper;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.repository.ActualRegistrationRepository;
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
class ActualRegistrationServiceImplTest {
static ActualRegistration ENTITY = ActualRegistration.builder()
        .id(1L)
        .build();
static ActualRegistrationDto DTO = ActualRegistrationDto.builder()
        .id(1L)
        .build();
    @Mock
   static ActualRegistrationRepository repository;
   @Mock
    static ActualRegistrationMapper mapper;
   @InjectMocks
   static ActualRegistrationServiceImpl service;

    @Test
    void save() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ActualRegistrationDto result = service.save(DTO);

        assertEquals(DTO, result);
    }

    @Test
    void givenInvalidData_whenSave_thenThrowException() {
        when(mapper.toEntity(DTO)).thenThrow(new RuntimeException("Ошибка при создании actual_registration:"));


        RuntimeException result = assertThrows(
                RuntimeException.class
                , () -> service.save(DTO)
        );

        assertEquals("Ошибка при создании actual_registration:", result.getMessage());
    }




    @Test
    void findAll() {
        when(mapper.toDtoList(List.of(ENTITY))).thenReturn(List.of(DTO));
        when(repository.findAll()).thenReturn(List.of(ENTITY));

        List<ActualRegistrationDto> result = service.findAll();

        assertEquals(List.of(DTO), result);
    }
    @Test
    void givenInvalidData_whenFindAll_thenThrowException() {
        when(repository.findAll())
                .thenThrow(new RuntimeException("Ошибка при получении списка actual_registration записей"));


        RuntimeException result = assertThrows(
                RuntimeException.class
                , () -> service.findAll()
        );

        assertEquals("Ошибка при получении списка actual_registration записей",result.getMessage());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ActualRegistrationDto result = service.findById(1L);

        assertEquals(DTO, result);
    }
    @Test
    void givenInvalidDate_whenFindById_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findById(1L)
        );


        assertEquals("Actual registration not found with ID: 1", result.getMessage());
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        doNothing().when(mapper).updateEntityFromDto(ENTITY, DTO);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ActualRegistrationDto result = service.update(1L, DTO);

        assertEquals(DTO, result);
    }

    @Test
    void givenInvalidDate_whenUpdate_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.update(1L, DTO)
        );


        assertEquals("Actual registration not found with ID:1", result.getMessage());
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
    @Test
    void givenInvalidData_whenDeleteById_thenThrowException() {
        doThrow(new EntityNotFoundException("Ошибка при удалении actual_registration"))
                .when(repository).deleteById(1L);

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.deleteById(1L)
        );
        assertEquals("Ошибка при удалении actual_registration", result.getMessage());
    }
}