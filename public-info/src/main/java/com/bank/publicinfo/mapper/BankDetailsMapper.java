package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.entity.License;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface BankDetailsMapper {

    @Mapping(target = "licenseIds", source = "licenses", qualifiedByName = "mapLicenseIds")
    @Mapping(target = "certificateIds", source = "certificates", qualifiedByName = "mapCertificateIds")
    BankDetailsDto toDto(BankDetails bankDetails);

    @Mapping(target = "licenses", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "id", ignore = true)
    BankDetails toModel(BankDetailsDto bankDetailsDto);

    @Named("mapLicenseIds")
    default Set<Long> mapLicenseIds(Set<License> licenses) {
        return licenses == null ? null : licenses.stream()
                .map(License::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapCertificateIds")
    default Set<Long> mapCertificateIds(Set<Certificate> certificates) {
        return certificates == null ? null : certificates.stream()
                .map(Certificate::getId)
                .collect(Collectors.toSet());
    }
}
