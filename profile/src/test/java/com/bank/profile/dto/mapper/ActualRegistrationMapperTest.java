package com.bank.profile.dto.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActualRegistrationMapperTest {

    ActualRegistrationDto DTO = ActualRegistrationDto.builder()
            .id(1L)
            .country("country")
            .region("region")
            .city("city")
            .district("district")
            .locality("locality")
            .street("street")
            .houseNumber("houseNumber")
            .houseBlock("houseBlock")
            .flatNumber("flatNumber")
            .index(1221L)
            .build();
    ActualRegistration ENTITY = ActualRegistration.builder()
            .id(1L)
            .country("country")
            .region("region")
            .city("city")
            .district("district")
            .locality("locality")
            .street("street")
            .houseNumber("houseNumber")
            .houseBlock("houseBlock")
            .flatNumber("flatNumber")
            .index(1221L)
            .build();

    ActualRegistrationMapper mapper = new ActualRegistrationMapperImpl();

    @Test
    void toEntity() {
        ActualRegistration result = mapper.toEntity(DTO);

        assertNotNull(result);
        assertEquals(ENTITY, result);

    }

    @Test
    void toDto() {
        ActualRegistrationDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void toDtoList() {
        List<ActualRegistrationDto> result = mapper.toDtoList(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);
    }

    @Test
    void updateEntityFromDto() {
        ActualRegistration result = new ActualRegistration();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getIndex(), result.getIndex());
    }
}
