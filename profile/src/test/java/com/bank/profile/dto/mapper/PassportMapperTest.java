package com.bank.profile.dto.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.Passport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PassportMapperTest {

    Passport ENTITY = Passport.builder()
            .id(1L)
            .series(1234)
            .number(4321L)
            .lastName("lastName")
            .firstName("firstName")
            .middleName("middleName")
            .gender("M")
            .birthDate(LocalDate.MAX)
            .birthPlace("test")
            .issuedBy("test")
            .dateOfIssue(LocalDate.MIN)
            .divisionCode(1)
            .expirationDate(LocalDate.MIN)
            .build();
    PassportDto DTO = PassportDto.builder()
            .id(1L)
            .series(1234)
            .number(4321L)
            .lastName("lastName")
            .firstName("firstName")
            .middleName("middleName")
            .gender("M")
            .birthDate(LocalDate.MAX)
            .birthPlace("test")
            .issuedBy("test")
            .dateOfIssue(LocalDate.MIN)
            .divisionCode(1)
            .expirationDate(LocalDate.MIN)
            .build();

    PassportMapper mapper = new PassportMapperImpl();

    @Test
    void toEntity() {
        Passport result = mapper.toEntity(DTO);
        assertNotNull(result);
        assertEquals(ENTITY, result);

    }

    @Test
    void toDto() {
        PassportDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void updateEntityFromDto() {
        Passport result = new Passport();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getFirstName(), result.getFirstName());
    }

    @Test
    void toDtoList() {
        List<PassportDto> result = mapper.toListDto(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);

    }
}
