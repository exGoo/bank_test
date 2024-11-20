package com.bank.profile.dto.mapper;

import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Passport;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {
    @Mapping(source = "profileId", target = "profile.id")
    AccountDetails toEntity(AccountDetailsDto accountDetails);

    @Mapping(target = "profileId", source = "profile.id")
    AccountDetailsDto toDto(AccountDetails accountDetails);

    List<AccountDetailsDto> toDtoList(List<AccountDetails> accountDetailsList);


}
