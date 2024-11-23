package com.bank.account.mapper;

import com.bank.account.dto.AccountDto;
import com.bank.account.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-23T18:07:34+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setPassportId( account.getPassportId() );
        accountDto.setAccountNumber( account.getAccountNumber() );
        accountDto.setBankDetailsId( account.getBankDetailsId() );
        accountDto.setMoney( account.getMoney() );
        accountDto.setNegativeBalance( account.getNegativeBalance() );
        accountDto.setProfileId( account.getProfileId() );

        return accountDto;
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        Account account = new Account();

        account.setPassportId( accountDto.getPassportId() );
        account.setAccountNumber( accountDto.getAccountNumber() );
        account.setBankDetailsId( accountDto.getBankDetailsId() );
        account.setMoney( accountDto.getMoney() );
        account.setNegativeBalance( accountDto.getNegativeBalance() );
        account.setProfileId( accountDto.getProfileId() );

        return account;
    }

    @Override
    public List<AccountDto> accountsToDto(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountDto> list = new ArrayList<AccountDto>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toDto( account ) );
        }

        return list;
    }
}
