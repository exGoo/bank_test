package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.entity.License;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LicenseMapperTest {

    private final LicenseMapper mapper = Mappers.getMapper(LicenseMapper.class);

    private final License license = License.builder()
            .id(1L)
            .build();

    private final LicenseDto licenseDto = LicenseDto.builder()
            .id(1L)
            .build();

    private final BankDetails bankDetails = BankDetails.builder()
            .id(3L)
            .build();

    @Test
    void toDto() {
        license.setBankDetails(bankDetails);
        LicenseDto dto = mapper.toDto(license);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBankDetailsId()).isEqualTo(3L);
    }

    @Test
    void toModel() {
        licenseDto.setBankDetailsId(3L);
        License certificate = mapper.toModel(licenseDto);
        assertThat(certificate).isNotNull();
        assertThat(certificate.getId()).isNull();
        assertThat(certificate.getBankDetails()).isNotNull();
        assertThat(certificate.getBankDetails().getId()).isEqualTo(3L);
    }
}
