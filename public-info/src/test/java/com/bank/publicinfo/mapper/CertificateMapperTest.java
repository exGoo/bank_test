package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERTIFICATE_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CertificateMapperTest {

    private final CertificateMapper mapper = Mappers.getMapper(CertificateMapper.class);

    @Test
    void toDto() {
        CertificateDto dto = mapper.toDto(TEST_CERTIFICATE_1);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(TEST_ID_1);
        assertThat(dto.getBankDetailsId()).isEqualTo(TEST_ID_1);
    }

    @Test
    void toModel() {
        Certificate certificate = mapper.toModel(TEST_CERTIFICATE_DTO);
        assertThat(certificate).isNotNull();
        assertThat(certificate.getId()).isNull();
        assertThat(certificate.getBankDetails()).isNotNull();
        assertThat(certificate.getBankDetails().getId()).isEqualTo(TEST_ID_1);
    }
}
