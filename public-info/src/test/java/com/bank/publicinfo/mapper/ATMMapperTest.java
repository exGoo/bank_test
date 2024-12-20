package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_STREET_1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ATMMapperTest {

    private ATMMapper mapper = Mappers.getMapper(ATMMapper.class);

    @Test
    void toDto() {
        ATMDto dto = mapper.toDto(TEST_ATM_1);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(TEST_ID_1);
        assertThat(dto.getAddress()).isEqualTo(TEST_STREET_1);
        assertThat(dto.getBranchId()).isEqualTo(TEST_ID_2);
    }

    @Test
    void toModel() {
        ATM atm = mapper.toModel(TEST_ATM_DTO);
        assertThat(atm).isNotNull();
        assertThat(atm.getId()).isNull();
        assertThat(atm.getAddress()).isEqualTo(TEST_STREET_1);
        assertThat(atm.getBranch()).isNull();
    }
}
