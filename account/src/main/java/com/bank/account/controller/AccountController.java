package com.bank.account.controller;

import com.bank.account.dto.AccountDto;
import com.bank.account.exception.AccountCreationException;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.model.Account;
import com.bank.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * AccountController является важным компонентом модуля Account,
 * предоставляющим интерфейс для взаимодействия с пользователем через HTTP запросы.
 * Этот контроллер управляет операциями, связанными с аккаунтами, включая создание,
 * получение, обновление и удаление аккаунтов. Контроллер обрабатывает HTTP-запросы,
 * преобразует данные между представлением (DTO) и моделью, а также взаимодействует
 * с сервисами для выполнения бизнес-логики.
 * */
@RestController
@RequestMapping("/")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    /**
     * В этом конструкторе происходит подвязка бизнес-логики и маппера, отвечающих за все основные действия над объектами в БД.
     * */
    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }


    /**
     * Метод createAccount отвечает за создание аккаунта посредством POST-запроса
     * @param accountDto включает в себя такие поля, как - Long passportId,
     *                   Long accountNumber, Long bankDetailsId, BigDecimal money,
     *                   Boolean negativeBalance, Long profileId
     *
     * @return возвращает только что созданного пользователя, а также назначенный ему id в БД.
     * */
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
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

    /**
     * Метод getAccounts отвечает за выдачу списка всех аккаунтов, находящихся в базе посредством GET-запроса
     *
     * @return возвращает список всех пользователей из БД.
     * */
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccounts() {
        log.info("AccountController: Получен запрос на просмотр списка аккаунтов в базе.");
        return new ResponseEntity<>(accountMapper.accountsToDto(accountService.findAll()), HttpStatus.OK);
    }

    /**
     * Метод deleteAccount отвечает за удаление аккаунта посредством DELETE-запроса
     * @param id включает в себя целевой id пользователя в БД, который необходимо удалить.
     *
     * @return возвращает ответ 200 ОК, с текстом "Аккаунт удален".
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long id) {
        log.info("AccountController: Получен запрос на удаление аккаунта с ID {}", id);
        accountService.delete(accountService.findById(id));
        if (accountService.findById(id) == null) {
            throw new AccountNotFoundException(id);
        }
        log.info("AccountController: Запрос на удаление аккаунта с ID {} удовлетворен, отправлен ответ.", id);
        return new ResponseEntity<>("Аккаунт удален", HttpStatus.OK);
    }

    /**
     * Метод findAccount отвечает за поиск аккаунта по его ID посредством GET-запроса
     * @param id включает в себя целевой id пользователя в БД, который необходимо найти.
     *
     * @return возвращает найденного пользователя, либо статус 404 NOT_FOUND, если такового не существует.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findAccount(@PathVariable("id") Long id) {
        log.info("AccountController: Получен запрос на поиск аккаунта с ID {}", id);
        Account account = accountService.findById(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        log.info("AccountController: Запрос на поиск аккаунта с ID {} удовлетворен, отправлен ответ.", id);
        return new ResponseEntity<>(accountMapper.toDto(account), HttpStatus.OK);
    }

    /**
     * Метод updateAccount отвечает за поиск аккаунта по его ID, а также его обновления посредством PATCH-запроса
     * @param id включает в себя целевой id пользователя в БД, который необходимо найти.
     * @param accountDto включает в себя такие поля, как - Long passportId,
     *                   Long accountNumber, Long bankDetailsId, BigDecimal money,
     *                   Boolean negativeBalance, Long profileId
     *
     * @return в случае успеха возвращает обновленный объект, в ином случае 404 NOT_FOUND.
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("id") Long id, @RequestBody AccountDto accountDto) {
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