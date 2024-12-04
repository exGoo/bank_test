package com.bank.profile.dto.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PassportMapper {

    Passport toEntity(PassportDto passport);

    @Mapping(source = "registration.id", target = "registrationId")
    PassportDto toDto(Passport passport);

    List<PassportDto> toListDto(List<Passport> passports);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registration", ignore = true)
    void updateEntityFromDto(@MappingTarget Passport passport, PassportDto passportDto);
}
