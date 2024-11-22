package com.bank.publicinfo.mapper;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    LicenseDto toDto(License license);
    License toModel(LicenseDto licenseDTO);

    @Mapping(target = "id", ignore = true)
    void createOrUpdateEntity(@MappingTarget License entity, LicenseDto dto);
}