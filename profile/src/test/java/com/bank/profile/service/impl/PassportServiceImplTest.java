package com.bank.profile.service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.mapper.PassportMapper;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Registration;
import com.bank.profile.repository.PassportRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassportServiceImplTest {

    Registration REGISTRATION = Registration.builder()
            .id(1L)
            .build();
    Passport ENTITY = Passport.builder()
            .id(1L)
            .registration(REGISTRATION)
            .build();
    PassportDto DTO = PassportDto.builder()
            .id(1L)
            .registrationId(1L)
            .build();

    @Mock
    PassportRepository repository;
    @Mock
    PassportMapper mapper;
    @Mock
    RegistrationRepository registrationRepository;
    @InjectMocks
    PassportServiceImpl service;

    @Test
    void save() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(REGISTRATION));
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        PassportDto result = service.save(DTO);

        assertEquals(DTO, result);
    }

    @Test
    void givenInvalidData_whenSave_thenThrowException() {
      when(mapper.toEntity(DTO)).thenReturn(ENTITY);
      when(registrationRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.save(DTO)
        );

        assertEquals("Registration not found with ID: 1", result.getMessage());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));
        when(mapper.toListDto(List.of(ENTITY))).thenReturn(List.of(DTO));

        List<PassportDto> result = service.findAll();

        assertEquals(List.of(DTO), result);
    }

    @Test
    void givenInvalidData_whenFindAll_thenThrowException() {
        when(repository.findAll())
                .thenThrow(new EntityNotFoundException("Ошибка при получении списка passport записей"));

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findAll()
        );

        assertEquals("Ошибка при получении списка passport записей", result.getMessage());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        PassportDto result = service.findById(1L);

        assertEquals(DTO, result);
    }

    @Test
    void givenInvalidDate_whenFindById_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findById(1L)
        );

        assertEquals("passport not found with ID: 1", result.getMessage());
    }

    @Test
    void update() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(ENTITY));
        when(registrationRepository.findById(1L))
                .thenReturn(Optional.of(REGISTRATION));
        doNothing().when(mapper).updateEntityFromDto(ENTITY, DTO);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        PassportDto result = service.update(1L, DTO);

       assertEquals(DTO, result);
    }

    @Test
    void givenInvalidDate_whenUpdate_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.update(1L, DTO)
        );

        assertEquals("Passport not found with ID: 1", result.getMessage());
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidData_whenDeleteById_thenThrowException() {
        doThrow(new EntityNotFoundException("Ошибка при удалении Passport"))
                .when(repository).deleteById(1L);

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.deleteById(1L)
        );
        assertEquals("Ошибка при удалении Passport", result.getMessage());
    }
}