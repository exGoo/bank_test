package com.bank.account.controller;

import com.bank.account.dto.AccountDto;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Account ACCOUNT = Account.builder()
            .id(1L)
            .passportId(12345L)
            .accountNumber(67890L)
            .bankDetailsId(98765L)
            .money(new BigDecimal("1000.00"))
            .negativeBalance(false)
            .profileId(54321L)
            .build();

    private static final AccountDto ACCOUNT_DTO = AccountDto.builder()
            .id(1L)
            .passportId(12345L)
            .accountNumber(67890L)
            .bankDetailsId(98765L)
            .money(new BigDecimal("1000.00"))
            .negativeBalance(false)
            .profileId(54321L)
            .build();

    @Test
    void createAccount() throws Exception {
        when(accountMapper.toEntity(any(AccountDto.class))).thenReturn(ACCOUNT);
        when(accountMapper.toDto(any(Account.class))).thenReturn(ACCOUNT_DTO);
        doNothing().when(accountService).save(any(Account.class));

        String accountDtoJson = objectMapper.writeValueAsString(ACCOUNT_DTO);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ACCOUNT_DTO.getId()));

        verify(accountService, times(1)).save(any(Account.class));
    }

    @Test
    void getAccounts() throws Exception {
        when(accountService.findAll()).thenReturn(List.of(ACCOUNT));
        when(accountMapper.accountsToDto(anyList())).thenReturn(List.of(ACCOUNT_DTO));

        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ACCOUNT_DTO.getId()));

        verify(accountService, times(1)).findAll();
    }

    @Test
    void deleteAccount() throws Exception {
        when(accountService.findById(anyLong())).thenReturn(ACCOUNT);
        doNothing().when(accountService).delete(any(Account.class));

        mockMvc.perform(delete("/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Аккаунт удален"));

        verify(accountService, times(1)).delete(ACCOUNT);
    }

    @Test
    void findAccount() throws Exception {
        when(accountService.findById(anyLong())).thenReturn(ACCOUNT);
        when(accountMapper.toDto(any(Account.class))).thenReturn(ACCOUNT_DTO);

        mockMvc.perform(get("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACCOUNT_DTO.getId()));

        verify(accountService, times(1)).findById(1L);
    }

    @Test
    void updateAccount() throws Exception {
        AccountDto updatedAccountDto = ACCOUNT_DTO.toBuilder().money(new BigDecimal("2000.0")).build();

        when(accountService.findById(anyLong())).thenReturn(ACCOUNT);
        when(accountMapper.toDto(any(Account.class))).thenReturn(updatedAccountDto);
        doNothing().when(accountService).update(any(Account.class));

        String updatedAccountDtoJson = objectMapper.writeValueAsString(updatedAccountDto);

        mockMvc.perform(patch("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAccountDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(updatedAccountDto.getMoney().toString()));

        verify(accountService, times(1)).findById(1L);
        verify(accountService, times(1)).update(any(Account.class));
    }
}
