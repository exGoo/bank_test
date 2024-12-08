package com.bank.profile.dto.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.Profile;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfileMapperTest {

    Profile ENTITY = Profile.builder()
            .id(1L)
            .phoneNumber(9653648L)
            .email("email@email.com")
            .nameOnCard("nameOnCard")
            .inn(12233L)
            .snils(13345532L)
            .build();
    ProfileDto DTO = ProfileDto.builder()
            .id(1L)
            .phoneNumber(9653648L)
            .email("email@email.com")
            .nameOnCard("nameOnCard")
            .inn(12233L)
            .snils(13345532L)
            .accountDetailsId(List.of())
            .build();

    ProfileMapper mapper = new ProfileMapperImpl();

    @Test
    void toEntity() {
        Profile result = mapper.toEntity(DTO);
        assertNotNull(result);
        assertEquals(ENTITY, result);
    }

    @Test
    void toDto() {
        ProfileDto result = mapper.toDto(ENTITY);
        assertNotNull(result);
        assertEquals(DTO, result);
    }

    @Test
    void updateEntityFromDto() {
        Profile result = new Profile();
        mapper.updateEntityFromDto(result, DTO);

        assertNotNull(result);
        assertEquals(ENTITY.getInn(), result.getInn());
    }

    @Test
    void toDtoList() {
        List<ProfileDto> result = mapper.toListDto(List.of(ENTITY));

        assertNotNull(result);
        assertEquals(List.of(DTO), result);
    }
}
