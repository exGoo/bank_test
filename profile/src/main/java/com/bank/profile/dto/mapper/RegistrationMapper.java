package com.bank.profile.dto.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RegistrationMapper {
    Registration toEntity(RegistrationDto registrationDto);

    RegistrationDto toDto(Registration registration);

    List<RegistrationDto> toListDto(List<Registration> registrations);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@MappingTarget Registration registration, RegistrationDto registrationDto);

}
