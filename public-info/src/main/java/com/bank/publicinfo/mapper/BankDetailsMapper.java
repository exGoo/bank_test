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
    BankDetailsDto modelToDto(BankDetails bankDetails);

    @InheritInverseConfiguration
    BankDetails dtoToModel(BankDetailsDto bankDetailsDto);

    @Mapping(target = "id", ignore = true)
    void createOrUpdateEntity(@MappingTarget BankDetails entity, BankDetailsDto dto);

}
