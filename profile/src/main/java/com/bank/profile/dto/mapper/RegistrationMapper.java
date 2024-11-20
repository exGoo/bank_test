package com.bank.profile.dto.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    Registration toEntity(RegistrationDto registrationDto);
    RegistrationDto toDto(Registration registration);
    List<RegistrationDto> toListDto(List<Registration> registrations);

}
