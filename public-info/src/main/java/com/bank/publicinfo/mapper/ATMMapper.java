package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ATMMapper {

    @Mapping(target = "branchId", source = "branch.id")
    ATMDto toDto(ATM atm);

    @Mapping(target = "branch.id", source = "branchId")
    ATM toModel(ATMDto atmDto);

}
