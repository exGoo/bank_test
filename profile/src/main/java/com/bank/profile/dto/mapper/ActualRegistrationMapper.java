package com.bank.profile.dto.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActualRegistrationMapper {

    ActualRegistration toEntity(ActualRegistrationDto dto);

    ActualRegistrationDto toDto(ActualRegistration entity);

    List<ActualRegistrationDto> toDtoList(List<ActualRegistration> entities);
}
