package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Set;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ATM_SET;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_2;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BranchMapperTest {

    private final BranchMapper mapper = Mappers.getMapper(BranchMapper.class);

    @Test
    void toDto() {
        BranchDto dto = mapper.toDto(TEST_BRANCH);
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(TEST_ID_1);
        assertThat(dto.getCity()).isEqualTo(TEST_CITY_1);
        assertThat(dto.getAtmsIds()).containsExactlyInAnyOrder(TEST_ID_1);
    }

    @Test
    void toModel() {
        Branch branch = mapper.toModel(TEST_BRANCH_DTO);
        assertThat(branch).isNotNull();
        assertThat(branch.getId()).isNull();
        assertThat(branch.getCity()).isEqualTo(TEST_CITY_1);
        assertThat(branch.getAtms()).isNull();
    }

    @Test
    void mapAtmsIds() {
        Set<Long> atmsIds = mapper.mapAtmsIds(TEST_ATM_SET);
        assertThat(atmsIds).containsExactlyInAnyOrder(TEST_ID_1, TEST_ID_2);
    }
}
