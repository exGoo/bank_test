package com.bank.profile.dto.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.Passport;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring", uses = {RegistrationMapper.class})
public interface PassportMapper {
    Passport toEntity(PassportDto passport);

    PassportDto toDto(Passport passport);

    List<PassportDto> toListDto(List<Passport> passports);

}
