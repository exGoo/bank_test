package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.entity.License;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BankDetailsMapperTest {

    private final BankDetailsMapper mapper = Mappers.getMapper(BankDetailsMapper.class);

    private final Certificate certificate1 = Certificate.builder()
            .id(10L)
            .build();

    private final Certificate certificate2 = Certificate.builder()
            .id(12L)
            .build();

    private final License license1 = License.builder()
            .id(10L)
            .build();

    private final License license2 = License.builder()
            .id(12L)
            .build();

    private final BankDetails bankDetails = BankDetails.builder()
            .id(1L)
            .name("MyBank")
            .build();

    private final BankDetailsDto bankDetailsDto = BankDetailsDto.builder()
            .id(1L)
            .name("MyBank")
            .licenseIds(Set.of(10L, 12L))
            .build();

    private final Set<License> licenses = new HashSet<>(Set.of(license1,license2));

    private final Set<Certificate> certificates = new HashSet<>(Set.of(certificate1,certificate2));

    @Test
    void toDto() {
        bankDetails.setCertificates(certificates);
        bankDetails.setLicenses(licenses);
        BankDetailsDto dto = mapper.toDto(bankDetails);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("MyBank");
        assertThat(dto.getLicenseIds()).containsExactlyInAnyOrder(10L, 12L);
        assertThat(dto.getCertificateIds()).containsExactlyInAnyOrder(10L, 12L);
    }

    @Test
    void toModel() {
        BankDetails bankDetails = mapper.toModel(bankDetailsDto);
        assertThat(bankDetails).isNotNull();
        assertThat(bankDetails.getId()).isNull();
        assertThat(bankDetails.getName()).isEqualTo("MyBank");
        assertThat(bankDetails.getLicenses()).isNull();
        assertThat(bankDetails.getCertificates()).isNull();
    }

    @Test
    void mapLicenseIds() {
        Set<Long> licenseIds = mapper.mapLicenseIds(licenses);
        assertThat(licenseIds).containsExactlyInAnyOrder(10L, 12L);
    }

    @Test
    void mapCertificateIds() {
        Set<Long> certificatesId = mapper.mapCertificateIds(certificates);
        assertThat(certificatesId).containsExactlyInAnyOrder(10L, 12L);
    }
}
