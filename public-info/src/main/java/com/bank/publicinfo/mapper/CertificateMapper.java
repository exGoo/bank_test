package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CertificateMapper {

    @Mapping(target = "bankDetailsId", source = "bankDetails.id")
    CertificateDto toDto(Certificate certificate);

    @Mapping(target = "bankDetails.id", source = "bankDetailsId")
    @Mapping(target = "id", ignore = true)
    Certificate toModel(CertificateDto certificateDTO);
}