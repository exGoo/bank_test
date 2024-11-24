package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LicenseMapper {

    @Mapping(target = "bankDetailsId", source = "bankDetails.id")
    @Mapping(target = "photo", ignore = true)
    LicenseDto toDto(License license);

    @Mapping(target = "bankDetails.id", source = "bankDetailsId")
    @Mapping(target = "photo", ignore = true)
    License toModel(LicenseDto licenseDTO);

}