package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.entity.AccountDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountDetailsMapperTest {
    AccountDetails ENTITY = AccountDetails.builder()
            .id(1L)
            .accountId(1L)
            .build();
    AccountDetailsDto DTO = AccountDetailsDto.builder()
            .id(1L)
            .accountId(1L)
            .build();

    AccountDetailsMapper mapper = new AccountDetailsMapperImpl();

    @Test
    void toEntity() {
         AccountDetails result = mapper.toEntity(DTO);
         assertNotNull(result);
         assertEquals(ENTITY, result);

    }

    @Test
    void toDto() {
        AccountDetailsDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void updateEntityFromDto() {
        AccountDetails result = new AccountDetails();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getAccountId(), result.getAccountId());
    }

    @Test
    void toDtoList() {
        List<AccountDetailsDto> result = mapper.toDtoList(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);

    }
}