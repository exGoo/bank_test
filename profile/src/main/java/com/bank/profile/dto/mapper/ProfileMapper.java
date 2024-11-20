package com.bank.profile.dto.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PassportMapper.class,
                                            ActualRegistrationMapper.class,
                                            AccountDetailsMapper.class})
public interface ProfileMapper {
    Profile toEntity(ProfileDto dto);

    ProfileDto toDto(Profile entity);

    List<ProfileDto> toListDto(List<Profile> entities);
}
