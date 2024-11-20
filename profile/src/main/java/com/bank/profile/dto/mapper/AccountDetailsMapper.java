package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.entity.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountDetailsMapper {

    AccountDetails toEntity(AccountDetailsDto accountDetails);

    @Mapping(source = "profile.id", target = "profileId")
    AccountDetailsDto toDto(AccountDetails accountDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    void updateEntityFromDto(@MappingTarget AccountDetails target, AccountDetailsDto sourse);

    List<AccountDetailsDto> toDtoList(List<AccountDetails> accountDetailsList);


}
