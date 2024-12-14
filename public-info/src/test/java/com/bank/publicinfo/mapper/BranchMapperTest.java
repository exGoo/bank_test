package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.ATM;
import com.bank.publicinfo.entity.Branch;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BranchMapperTest {

    private final BranchMapper mapper = Mappers.getMapper(BranchMapper.class);

    private final Branch branch = Branch.builder()
            .id(1L)
            .city("Moscow")
            .build();

    private final BranchDto branchDto = BranchDto.builder()
            .id(1L)
            .city("Minsk")
            .build();

    private final ATM atm1 = ATM.builder()
            .id(2L)
            .address("MyStreet")
            .build();

    private final ATM atm2 = ATM.builder()
            .id(3L)
            .address("YourStreet")
            .build();

    private final Set<ATM> atms = new HashSet<>(Set.of(atm1, atm2));

    @Test
    void toDto() {
        branch.setAtms(atms);
        BranchDto dto = mapper.toDto(branch);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getCity()).isEqualTo("Moscow");
        assertThat(dto.getAtmsIds()).containsExactlyInAnyOrder(2L, 3L);
    }

    @Test
    void toModel() {
        Branch branch = mapper.toModel(branchDto);
        assertThat(branch).isNotNull();
        assertThat(branch.getId()).isNull();
        assertThat(branch.getCity()).isEqualTo("Minsk");
        assertThat(branch.getAtms()).isNull();
    }

    @Test
    void mapAtmsIds() {
        Set<Long> atmsIds = mapper.mapAtmsIds(atms);
        assertThat(atmsIds).containsExactlyInAnyOrder(2L, 3L);
    }
}
