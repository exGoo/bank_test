package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateDto toDTO(Certificate certificate);
    Certificate toEntity(CertificateDto certificateDTO);

    @Mapping(target = "id", ignore = true)
    void createOrUpdateEntity(@MappingTarget Certificate entity, CertificateDto dto);
}