package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ATMMapper {
    ATMDto toDto(ATM atm);

    ATM toModel(ATMDto atmDto);

    @Mapping(target = "id", ignore = true)
    void createOrUpdate(@MappingTarget ATM entity, ATMDto dto);

}
