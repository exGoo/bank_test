package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LicenseMapper {

    @Mapping(target = "bankDetailsId", source = "bankDetails.id")
    LicenseDto toDto(License license);

    @Mapping(target = "bankDetails.id", source = "bankDetailsId")
    @Mapping(target = "id", ignore = true)
    License toModel(LicenseDto licenseDTO);
}