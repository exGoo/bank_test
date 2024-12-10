package com.bank.account.mapper;

import com.bank.account.dto.AccountDto;
import com.bank.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);

    List<AccountDto> accountsToDto(List<Account> accounts);
}
