package com.bank.publicinfo.mapper;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    LicenseDto toDTO(License license);
    License toEntity(LicenseDto licenseDTO);

    @Mapping(target = "id", ignore = true)
    void createOrUpdateEntity(@MappingTarget License entity, LicenseDto dto);
}