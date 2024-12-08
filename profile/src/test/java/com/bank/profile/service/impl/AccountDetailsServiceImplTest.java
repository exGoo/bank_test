package com.bank.profile.service.impl;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.dto.mapper.AccountDetailsMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    static AccountDetailsDto DTO = AccountDetailsDto.builder()
            .id(1L)
            .profileId(1L)
            .build();
    static Profile PROFILE = Profile.builder()
            .id(1L)
            .build();
    static AccountDetails ENTITY = AccountDetails.builder()
            .id(1L)
            .profile(PROFILE)
            .build();

    @Mock
    static AccountDetailsRepository repository;
    @Mock
    static AccountDetailsMapper mapper;
    @Mock
    static ProfileRepository profileRepository;

    @InjectMocks
    static AccountDetailsServiceImpl accountDetailsServiceImpl;

    static Stream<Arguments> provideInvalidDataForUpdate() {
        return Stream.of(
                Arguments.of(
                        1L,
                        AccountDetailsDto.builder().build(),
                        "Account details not found with ID: 1",
                        (Runnable) () -> when(repository.findById(1L)).thenReturn(Optional.empty())
                ),
                Arguments.of(1L,
                        AccountDetailsDto.builder().id(1L).profileId(1L).build(),
                        "Profile not found with ID: 1",
                        (Runnable) () -> {
                            when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
                            when(profileRepository.findById(1L)).thenReturn(Optional.empty());
                        }
                )
        );
    }

    @Test
    void save() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(PROFILE));
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsDto result = accountDetailsServiceImpl.save(DTO);

        assertEquals(DTO.getId(), result.getId());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(ENTITY));
        when(mapper.toDtoList(List.of(ENTITY))).thenReturn(List.of(DTO));

        List<AccountDetailsDto> result = accountDetailsServiceImpl.findAll();

        assertEquals(DTO.getId(), result.get(0).getId());
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsDto result = accountDetailsServiceImpl.findById(1L);

        assertEquals(DTO.getId(), result.getId());
    }

    @Test
    void update() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        doNothing().when(mapper).updateEntityFromDto(ENTITY, DTO);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(PROFILE));
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsDto result = accountDetailsServiceImpl.update(1L, DTO);

        assertEquals(DTO.getId(), result.getId());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataForUpdate")
    void updateInvalid(Long id,
                       AccountDetailsDto accountDetailsDto,
                       String expectedMessage,
                       Runnable mockSetup) {
        mockSetup.run();

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> accountDetailsServiceImpl.update(id, accountDetailsDto)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void givenValidId_whenDeleteCalled_thenEntityDeleted() {
        doNothing().when(repository).deleteById(1L);

        accountDetailsServiceImpl.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void givenInvalidId_whenDeleteCalled_thenThrowException() {
        doThrow(new EntityNotFoundException()).when(repository).deleteById(999L);

        EntityNotFoundException result = assertThrows(EntityNotFoundException.class
                , () -> accountDetailsServiceImpl.deleteById(999L)
        );
        assertEquals(null, result.getMessage());
    }
}
