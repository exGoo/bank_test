package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LICENSE_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LicenseMapperTest {

    private final LicenseMapper mapper = Mappers.getMapper(LicenseMapper.class);

    @Test
    void toDto() {
        LicenseDto dto = mapper.toDto(TEST_LICENSE_1);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(TEST_ID_1);
        assertThat(dto.getBankDetailsId()).isEqualTo(TEST_ID_1);
    }

    @Test
    void toModel() {
        License license = mapper.toModel(TEST_LICENSE_DTO);
        assertThat(license).isNotNull();
        assertThat(license.getId()).isNull();
        assertThat(license.getBankDetails()).isNotNull();
        assertThat(license.getBankDetails().getId()).isEqualTo(TEST_ID_1);
    }
}
