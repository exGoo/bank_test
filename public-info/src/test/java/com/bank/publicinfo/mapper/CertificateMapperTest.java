package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CertificateMapperTest {

    private final CertificateMapper mapper = Mappers.getMapper(CertificateMapper.class);

    private final Certificate certificate = Certificate.builder()
            .id(1L)
            .build();

    private final CertificateDto certificateDto = CertificateDto.builder()
            .id(1L)
            .build();

    private final BankDetails bankDetails = BankDetails.builder()
            .id(3L)
            .build();

    @Test
    void toDto() {
        certificate.setBankDetails(bankDetails);
        CertificateDto dto = mapper.toDto(certificate);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBankDetailsId()).isEqualTo(3L);
    }

    @Test
    void toModel() {
        certificateDto.setBankDetailsId(3L);
        Certificate certificate = mapper.toModel(certificateDto);
        assertThat(certificate).isNotNull();
        assertThat(certificate.getId()).isNull();
        assertThat(certificate.getBankDetails()).isNotNull();
        assertThat(certificate.getBankDetails().getId()).isEqualTo(3L);
    }
}
