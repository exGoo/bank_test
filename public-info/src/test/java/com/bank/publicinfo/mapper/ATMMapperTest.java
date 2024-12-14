package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.ATMDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.entity.Branch;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ATMMapperTest {

    private final ATMMapper mapper = Mappers.getMapper(ATMMapper.class);

    private final ATM atm = ATM.builder()
            .id(1L)
            .address("MyStreet")
            .build();

    private final ATMDto atmDto = ATMDto.builder()
            .id(1L)
            .address("YourStreet")
            .build();

    private final Branch branch = Branch.builder()
            .id(3L)
            .build();

    @Test
    void toDto() {
        atm.setBranch(branch);
        ATMDto dto = mapper.toDto(atm);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getAddress()).isEqualTo("MyStreet");
        assertThat(dto.getBranchId()).isEqualTo(3L);
    }

    @Test
    void toModel() {
        atmDto.setBranchId(3L);
        ATM atm = mapper.toModel(atmDto);
        assertThat(atm).isNotNull();
        assertThat(atm.getId()).isNull();
        assertThat(atm.getAddress()).isEqualTo("YourStreet");
        assertThat(atm.getBranch()).isNull();
    }
}
