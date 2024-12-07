package com.bank.profile.service.impl;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.mapper.ProfileMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    static ProfileDto DTO = ProfileDto.builder()
            .id(1l)
            .passportId(1l)
            .actualRegistrationId(1l)
            .accountDetailsId(List.of(1l, 2l))
            .build();
    static Passport PASSPORT = Passport.builder()
            .id(1l)
            .build();
    static ActualRegistration ACTUAL_REGISTRATION = ActualRegistration.builder()
            .id(1l)
            .build();
    static List<AccountDetails> ACCOUNT_DETAILS = List.of(
            AccountDetails.builder().id(1l).build(),
            AccountDetails.builder().id(2l).build());
    static Profile ENTITY = Profile.builder()
            .id(1l)
            .passport(PASSPORT)
            .actualRegistration(ACTUAL_REGISTRATION)
            .accountDetails(ACCOUNT_DETAILS)
            .build();
    @Mock
    static ProfileRepository repository;
    @Mock
    static ProfileMapper mapper;
    @Mock
    static PassportRepository passportRepository;
    @Mock
    static AccountDetailsRepository accountDetailsRepository;
    @Mock
    static ActualRegistrationRepository actualRegistrationRepository;
    @InjectMocks
    static ProfileServiceImpl service;

    @Test
    void save() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(passportRepository.findById(DTO.getPassportId()))
                .thenReturn(Optional.of(PASSPORT));
        when(actualRegistrationRepository.findById(DTO.getActualRegistrationId()))
                .thenReturn(Optional.of(ACTUAL_REGISTRATION));
        when(accountDetailsRepository.findAllById(DTO.getAccountDetailsId()))
                .thenReturn(ACCOUNT_DETAILS);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = service.save(DTO);

        assertEquals(DTO.getId(), result.getId());
        assertEquals(DTO.getPassportId(), result.getPassportId());
        assertEquals(DTO.getActualRegistrationId(), result.getActualRegistrationId());
        assertEquals(DTO.getAccountDetailsId(), result.getAccountDetailsId());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));
        when(mapper.toListDto(List.of(ENTITY))).thenReturn(List.of(DTO));

        List<ProfileDto> result = service.findAll();

        assertEquals(ENTITY.getId(), result.get(0).getId());
    }

    @Test
    void givenValidId_whenFindById_thenReturnProfileDto() {
        when(repository.findById(DTO.getId())).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = service.findById(DTO.getId());

        assertEquals(DTO.getId(), result.getId());
    }

    @Test
    void givenInvalidId_whenFindById_thenThrowException() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException result = assertThrows(
                EntityNotFoundException.class
                , () -> service.findById(2l)
        );

        assertEquals("profile not found with ID: 2", result.getMessage());
    }

    @Test
    void givenValidData_whenUpdateCalled_thenReturnProfileDto() {
        when(repository.findById(DTO.getId()))
                .thenReturn(Optional.of(ENTITY));
        when(passportRepository.findById(DTO.getPassportId()))
                .thenReturn(Optional.of(PASSPORT));
        when(actualRegistrationRepository.findById(DTO.getActualRegistrationId()))
                .thenReturn(Optional.of(ACTUAL_REGISTRATION));
        when(accountDetailsRepository.findAllById(DTO.getAccountDetailsId()))
                .thenReturn(ACCOUNT_DETAILS);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = service.update(1l, DTO);

        assertEquals(DTO.getId(), result.getId());
        assertEquals(DTO.getPassportId(), result.getPassportId());
        assertEquals(DTO.getActualRegistrationId(), result.getActualRegistrationId());
        assertEquals(DTO.getAccountDetailsId(), result.getAccountDetailsId());

    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataForUpdate")
    void givenInvalidData_whenUpdateCalled_thenThrowEntityNotFoundException(
            Long id, ProfileDto dto, String exceptionMessage, Runnable mockSetup) {

        mockSetup.run();

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.update(id, dto)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    static Stream<Arguments> provideInvalidDataForUpdate() {
        return Stream.of(
                Arguments.of(
                        1L,
                        ProfileDto.builder().build(),
                        "profile not found with ID: 1",
                        (Runnable) () -> when(repository.findById(any(Long.class))).thenReturn(Optional.empty())
                ),
                Arguments.of(
                        1L,
                        ProfileDto.builder()
                                .id(1L)
                                .passportId(2L)
                                .build(),
                        "Passport not found with ID: 2",
                        (Runnable) () -> {
                            when(repository.findById(any(Long.class))).thenReturn(Optional.of(ENTITY));
                            when(passportRepository.findById(2L)).thenReturn(Optional.empty());
                        }
                ),
                Arguments.of(
                        1L,
                        ProfileDto.builder()
                                .id(1L)
                                .passportId(2L)
                                .actualRegistrationId(3L)
                                .build(),
                        "ActualRegistration not found with ID: 3",
                        (Runnable) () -> {
                            when(repository.findById(any(Long.class))).thenReturn(Optional.of(ENTITY));
                            when(passportRepository.findById(any(Long.class))).thenReturn(Optional.of(PASSPORT));
                            when(actualRegistrationRepository.findById(3L)).thenReturn(Optional.empty());
                        }
                )
        );
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(DTO.getId());

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidData_whenDeleteById_thenThrowException() {
        doThrow(new EntityNotFoundException()).when(repository).deleteById(999L);

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class
                , () -> service.deleteById(999L)
        );
        assertEquals(null, result.getMessage());
    }
}