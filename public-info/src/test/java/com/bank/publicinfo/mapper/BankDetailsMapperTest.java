package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Set;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BANK_DETAILS;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CERETIFICATE_SET;
import static com.bank.publicinfo.utils.TestsUtils.TEST_DETAILS_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENCE_SET;
import static com.bank.publicinfo.utils.TestsUtils.TEST_NAME;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BankDetailsMapperTest {

    private final BankDetailsMapper mapper = Mappers.getMapper(BankDetailsMapper.class);

    @Test
    void toDto() {
        BankDetailsDto dto = mapper.toDto(TEST_BANK_DETAILS);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(TEST_ID_1);
        assertThat(dto.getName()).isEqualTo(TEST_NAME);
        assertThat(dto.getLicenseIds()).containsExactlyInAnyOrder(TEST_ID_1, TEST_ID_2);
        assertThat(dto.getCertificateIds()).containsExactlyInAnyOrder(TEST_ID_2, TEST_ID_1);
    }

    @Test
    void toModel() {
        BankDetails bankDetails = mapper.toModel(TEST_DETAILS_DTO);
        assertThat(bankDetails).isNotNull();
        assertThat(bankDetails.getId()).isNull();
        assertThat(bankDetails.getName()).isEqualTo(TEST_NAME);
        assertThat(bankDetails.getLicenses()).isNull();
        assertThat(bankDetails.getCertificates()).isNull();
    }

    @Test
    void mapLicenseIds() {
        Set<Long> licenseIds = mapper.mapLicenseIds(TEST_LICENCE_SET);
        assertThat(licenseIds).containsExactlyInAnyOrder(TEST_ID_1, TEST_ID_2);
    }

    @Test
    void mapCertificateIds() {
        Set<Long> certificatesId = mapper.mapCertificateIds(TEST_CERETIFICATE_SET);
        assertThat(certificatesId).containsExactlyInAnyOrder(TEST_ID_1, TEST_ID_2);
    }
}
