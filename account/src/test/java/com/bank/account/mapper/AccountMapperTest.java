package com.bank.account.mapper;

import com.bank.account.dto.AccountDto;
import com.bank.account.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountMapperTest {
    public static final Long ID = 1L;
    public static final Long PASSPORT_ID = 2L;
    public static final Long ACCOUNT_NUMBER = 3L;
    public static final Long BANK_DETAILS_ID = 4L;
    public static final BigDecimal MONEY = new BigDecimal(5);
    public static final Boolean NEGATIVE_BALANCE = false;
    public static final Long PROFILE_ID = 7L;

    private static final AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    private Account account;
    private Account accountTwo;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(ID)
                .passportId(PASSPORT_ID)
                .accountNumber(ACCOUNT_NUMBER)
                .bankDetailsId(BANK_DETAILS_ID)
                .money(MONEY)
                .negativeBalance(NEGATIVE_BALANCE)
                .profileId(PROFILE_ID)
                .build();

        accountTwo = Account.builder()
                .id(ID)
                .passportId(PASSPORT_ID)
                .accountNumber(ACCOUNT_NUMBER)
                .bankDetailsId(BANK_DETAILS_ID)
                .money(MONEY)
                .negativeBalance(NEGATIVE_BALANCE)
                .profileId(PROFILE_ID)
                .build();

        accountDto = AccountDto.builder()
                .id(ID)
                .passportId(PASSPORT_ID)
                .accountNumber(ACCOUNT_NUMBER)
                .bankDetailsId(BANK_DETAILS_ID)
                .money(MONEY)
                .negativeBalance(NEGATIVE_BALANCE)
                .profileId(PROFILE_ID)
                .build();
    }

    @Test
    void toDto() {
        AccountDto accountToDto = MAPPER.toDto(account);

        assertNotNull(accountToDto);
        assertEquals(ID, accountToDto.getId());
        assertEquals(PASSPORT_ID, accountToDto.getPassportId());
        assertEquals(ACCOUNT_NUMBER, accountToDto.getAccountNumber());
        assertEquals(BANK_DETAILS_ID, accountToDto.getBankDetailsId());
        assertEquals(MONEY, accountToDto.getMoney());
        assertEquals(NEGATIVE_BALANCE, accountToDto.getNegativeBalance());
        assertEquals(PROFILE_ID, accountToDto.getProfileId());
    }

    @Test
    void toEntity() {
        Account accountDtoToEntity = MAPPER.toEntity(accountDto);

        assertNotNull(accountDtoToEntity);
        assertEquals(ID, accountDtoToEntity.getId());
        assertEquals(PASSPORT_ID, accountDtoToEntity.getPassportId());
        assertEquals(ACCOUNT_NUMBER, accountDtoToEntity.getAccountNumber());
        assertEquals(BANK_DETAILS_ID, accountDtoToEntity.getBankDetailsId());
        assertEquals(MONEY, accountDtoToEntity.getMoney());
        assertEquals(NEGATIVE_BALANCE, accountDtoToEntity.getNegativeBalance());
        assertEquals(PROFILE_ID, accountDtoToEntity.getProfileId());
    }

    @Test
    void accountsToDto() {
        List<Account> accountList = List.of(account, accountTwo);
        List<AccountDto> accountDtoList = MAPPER.accountsToDto(accountList);

        assertNotNull(accountDtoList);
        assertEquals(2, accountDtoList.size());

        assertEquals(ID, accountDtoList.get(0).getId());
        assertEquals(PASSPORT_ID, accountDtoList.get(0).getPassportId());
        assertEquals(ACCOUNT_NUMBER, accountDtoList.get(0).getAccountNumber());
        assertEquals(BANK_DETAILS_ID, accountDtoList.get(0).getBankDetailsId());
        assertEquals(MONEY, accountDtoList.get(0).getMoney());
        assertEquals(NEGATIVE_BALANCE, accountDtoList.get(0).getNegativeBalance());
        assertEquals(PROFILE_ID, accountDtoList.get(0).getProfileId());

        assertEquals(ID, accountDtoList.get(1).getId());
        assertEquals(PASSPORT_ID, accountDtoList.get(1).getPassportId());
        assertEquals(ACCOUNT_NUMBER, accountDtoList.get(1).getAccountNumber());
        assertEquals(BANK_DETAILS_ID, accountDtoList.get(1).getBankDetailsId());
        assertEquals(MONEY, accountDtoList.get(1).getMoney());
        assertEquals(NEGATIVE_BALANCE, accountDtoList.get(1).getNegativeBalance());
        assertEquals(PROFILE_ID, accountDtoList.get(1).getProfileId());
    }
}