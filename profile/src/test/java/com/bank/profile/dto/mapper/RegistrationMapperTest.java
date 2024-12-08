package com.bank.profile.dto.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.Registration;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegistrationMapperTest {

    Registration ENTITY = Registration.builder()
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
    RegistrationDto DTO = RegistrationDto.builder()
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

    RegistrationMapper mapper = new RegistrationMapperImpl();

    @Test
    void toEntity() {
        Registration result = mapper.toEntity(DTO);
        assertNotNull(result);
        assertEquals(ENTITY, result);
    }

    @Test
    void toDto() {
        RegistrationDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void updateEntityFromDto() {
        Registration result = new Registration();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getIndex(), result.getIndex());
    }

    @Test
    void toDtoList() {
        List<RegistrationDto> result = mapper.toListDto(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);
    }
}
