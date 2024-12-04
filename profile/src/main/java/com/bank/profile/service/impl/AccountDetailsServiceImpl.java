package com.bank.profile.service.impl;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.AccountDetailsDto;
import com.bank.profile.dto.mapper.AccountDetailsMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.AccountDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AccountDetailsServiceImpl implements AccountDetailsService {

    AccountDetailsRepository repository;
    AccountDetailsMapper mapper;
    ProfileRepository profileRepository;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository repository, AccountDetailsMapper mapper, ProfileRepository profileRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.profileRepository = profileRepository;
    }

    @AuditSave(entityType = "account_details")
    @Override
    public AccountDetailsDto save(AccountDetailsDto accountDetails) {
        log.info("попытка сохранить Account_details: {}", accountDetails);
        try {
            AccountDetails newDetails = mapper.toEntity(accountDetails);
            Profile profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                    () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
            newDetails.setProfile(profile);
            AccountDetails save = repository.save(newDetails);
            log.info("Account_details: сохранен с ID: {}", save.getId());
            return mapper.toDto(save);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при сохранении Account_details {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AccountDetailsDto> findAll() {
        log.info("Попытка получить список account_details");
        List<AccountDetails> list = repository.findAll();
        log.info("Список account_details успешно получен. Количество записей: {}", list.size());
        return mapper.toDtoList(list);
    }

    @Override
    public AccountDetailsDto findById(Long id) {
        log.info("Попытка получить account_details с ID: {}", id);
        try {
            AccountDetailsDto accountDetails = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Account details not found with ID: " + id)));
            log.info("account_details успешно получена: {}", accountDetails);
            return accountDetails;

        } catch (EntityNotFoundException e) {
            log.error("account_details с ID: {} не найден", id);
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = "account_details")
    public AccountDetailsDto update(Long id, AccountDetailsDto accountDetails) {
        log.info("Попытка обновить account_details с ID: {}, новые данные: {}", id, accountDetails);
        try {
            AccountDetails oldDetails = repository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Account details not found with ID: " + id));
            log.info("account_details с ID: {} найден для обновления", id);
            Profile profile = oldDetails.getProfile();
            if (accountDetails.getProfileId() != null) {
                profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                        () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
                log.info("profile с ID: {} найден для обновления", accountDetails.getProfileId());
            }
            mapper.updateEntityFromDto(oldDetails, accountDetails);
            oldDetails.setProfile(profile);
            AccountDetails details = repository.save(oldDetails);
            log.info("account_details с ID: {} успешно обновлена", id);
            return mapper.toDto(details);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при обновлении account_details с ID: {},  данные: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления account_details с ID: {}", id);
        try {
            repository.deleteById(id);
            log.info("account_details с ID: {} удален", id);

        } catch (Exception e) {
            log.error("Ошибка при удалении account_details: {}", e.getMessage());
            throw e;
        }
    }
}
