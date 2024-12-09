package com.bank.account.controller;

import com.bank.account.dto.AccountDto;
import com.bank.account.exception.AccountCreationException;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "AccountController", description = "Контроллер для работы с аккаунтами пользователей")
@RestController
@RequestMapping("/")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    @Operation(
            summary = "Создать аккаунт пользователя",
            description = "Возвращает только что созданный аккаунт пользователя, включая его ID в Базе Данных"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Такой ответ подразумевает неверные данные",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<AccountDto> createAccount(@Parameter(description = "Данные для создания Аккаунта")
                                                        @Valid
                                                        @RequestBody AccountDto accountDto) {
        try {
            log.info("AccountController: Запрос на создание аккаунта - {}", accountDto);
            accountDto.setId(null);
            Account account = accountMapper.toEntity(accountDto);
            accountService.save(account);
            AccountDto accountDtoResponse = accountMapper.toDto(account);
            log.info("AccountController: Запрос на создание аккаунта удовлетворен, аккаунт {} создан", account);
            return new ResponseEntity<>(accountDtoResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("AccountController: Ошибка при создании аккаунта.");
            throw new AccountCreationException("Ошибка при создании аккаунта, переданы null, или не все необходимые данные.");
        }
    }

    @GetMapping
    @Operation(
            summary = "Список аккаунтов",
            description = "Возвращает список пользователей из БД"
    )
    @ApiResponse(responseCode = "200", description = "Успешный ответ",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<List<AccountDto>> getAccounts() {
        log.info("AccountController: Получен запрос на просмотр списка аккаунтов в базе.");
        return new ResponseEntity<>(accountMapper.accountsToDto(accountService.findAll()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление аккаунта пользователя",
            description = "Удаляет аккаунт пользователя из БД"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Такой ответ подразумевает неверно указанный ID аккаунта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<String> deleteAccount(@Parameter(description = "ID Аккаунта")
                                                    @PathVariable("id") Long id) {
        log.info("AccountController: Получен запрос на удаление аккаунта с ID {}", id);
        if (accountService.findById(id) == null) {
            throw new AccountNotFoundException(id);
        }
        accountService.delete(accountService.findById(id));
        log.info("AccountController: Запрос на удаление аккаунта с ID {} удовлетворен, отправлен ответ.", id);
        return new ResponseEntity<>("Аккаунт удален", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск аккаунта пользователя",
            description = "Поиск аккаунта пользователя из БД и возврат его клиенту"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Такой ответ подразумевает неверно указанный ID аккаунта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<AccountDto> findAccount(@Parameter(description = "ID Аккаунта")
                                                      @PathVariable("id") Long id) {
        log.info("AccountController: Получен запрос на поиск аккаунта с ID {}", id);
        Account account = accountService.findById(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        log.info("AccountController: Запрос на поиск аккаунта с ID {} удовлетворен, отправлен ответ.", id);
        return new ResponseEntity<>(accountMapper.toDto(account), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Обновление аккаунта пользователя",
            description = "Обновляет аккаунт пользователя (либо конкретные его поля) в БД"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Такой ответ подразумевает неверно указанный ID аккаунта",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<AccountDto> updateAccount(@Parameter(description = "ID Аккаунта")
                                                        @PathVariable("id") Long id,
                                                    @Parameter(description = "Данные обновленного Аккаунта")
                                                    @RequestBody AccountDto accountDto) {
        log.info("AccountController: Получен запрос на обновление аккаунта с ID {}", id);
        Account account = accountService.findById(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }

        if (accountDto.getPassportId() != null) {
            account.setPassportId(accountDto.getPassportId());
        }
        if (accountDto.getAccountNumber() != null) {
            account.setAccountNumber(accountDto.getAccountNumber());
        }
        if (accountDto.getBankDetailsId() != null) {
            account.setBankDetailsId(accountDto.getBankDetailsId());
        }
        if (accountDto.getMoney() != null) {
            account.setMoney(accountDto.getMoney());
        }
        if (accountDto.getNegativeBalance() != null) {
            account.setNegativeBalance(accountDto.getNegativeBalance());
        }
        if (accountDto.getProfileId() != null) {
            account.setProfileId(accountDto.getProfileId());
        }
        accountService.update(account);
        log.info("AccountController: Запрос на обновление аккаунта с ID {} удовлетворен, отправлен ответ.", id);
        return new ResponseEntity<>(accountMapper.toDto(account), HttpStatus.OK);
    }

}