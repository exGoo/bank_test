package com.bank.profile.dto.mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {

    Profile toEntity(ProfileDto dto);

    @Mapping(source = "passport.id", target = "passportId")
    @Mapping(source = "actualRegistration.id", target = "actualRegistrationId")
    @Mapping(source = "accountDetails", target = "accountDetailsId")
    ProfileDto toDto(Profile entity);

    default List<Long> mapAccountDetailsToIds(List<AccountDetails> accountDetails) {
        return accountDetails != null
                ? accountDetails.stream().map(AccountDetails::getId).collect(Collectors.toList())
                : Collections.emptyList();
    }

    List<ProfileDto> toListDto(List<Profile> entities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passport", ignore = true)
    @Mapping(target = "actualRegistration", ignore = true)
    @Mapping(target = "accountDetails", ignore = true)
    void updateEntityFromDto(@MappingTarget Profile entity, ProfileDto dto);
}
