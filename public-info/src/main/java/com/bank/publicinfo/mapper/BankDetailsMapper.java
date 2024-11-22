package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    @Mapping(target = "licenses", source = "licenses")
    @Mapping(target = "certificates", source = "certificates")
    BankDetailsDto toDto(BankDetails bankDetails);

    @InheritInverseConfiguration
    BankDetails toModel(BankDetailsDto bankDetailsDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget BankDetails entity, BankDetailsDto dto);

}
