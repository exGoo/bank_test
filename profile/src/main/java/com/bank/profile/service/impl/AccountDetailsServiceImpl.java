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

    private static final String ENTITY_TYPE = "account_details";

    AccountDetailsRepository repository;
    AccountDetailsMapper mapper;
    ProfileRepository profileRepository;

    @Autowired
    public AccountDetailsServiceImpl(AccountDetailsRepository repository, AccountDetailsMapper mapper, ProfileRepository profileRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.profileRepository = profileRepository;
    }

    @AuditSave(entityType = ENTITY_TYPE)
    @Override
    public AccountDetailsDto save(AccountDetailsDto accountDetails) {
        log.info("попытка сохранить {}: {}", ENTITY_TYPE, accountDetails);
        try {
            AccountDetails newDetails = mapper.toEntity(accountDetails);
            Profile profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                    () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
            newDetails.setProfile(profile);
            AccountDetails save = repository.save(newDetails);
            log.info("{}: сохранен с ID: {}", ENTITY_TYPE, save.getId());
            return mapper.toDto(save);
        } catch (EntityNotFoundException e) {
            log.error("ошибка при сохранении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AccountDetailsDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        List<AccountDetails> list = repository.findAll();
        log.info("Список {} успешно получен. Количество записей: {}", ENTITY_TYPE, list.size());
        return mapper.toDtoList(list);
    }

    @Override
    public AccountDetailsDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        try {
            AccountDetailsDto accountDetails = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Account details not found with ID: " + id)));
            log.info("{} успешно получена: {}", ENTITY_TYPE, accountDetails);
            return accountDetails;

        } catch (EntityNotFoundException e) {
            log.error("{} с ID: {} не найден", ENTITY_TYPE, id);
            throw e;
        }
    }

    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public AccountDetailsDto update(Long id, AccountDetailsDto accountDetails) {
        log.info("Попытка обновить {} с ID: {}, новые данные: {}", ENTITY_TYPE, id, accountDetails);
        try {
            AccountDetails oldDetails = repository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Account details not found with ID: " + id));
            log.info("{} с ID: {} найден для обновления", ENTITY_TYPE, id);
            Profile profile = oldDetails.getProfile();
            if (accountDetails.getProfileId() != null) {
                profile = profileRepository.findById(accountDetails.getProfileId()).orElseThrow(
                        () -> new EntityNotFoundException("Profile not found with ID: " + accountDetails.getProfileId()));
                log.info("profile с ID: {} найден для обновления", accountDetails.getProfileId());
            }
            mapper.updateEntityFromDto(oldDetails, accountDetails);
            oldDetails.setProfile(profile);
            AccountDetails details = repository.save(oldDetails);
            log.info("{} с ID: {} успешно обновлена", ENTITY_TYPE, id);
            return mapper.toDto(details);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при обновлении {} с ID: {},  данные: {}", ENTITY_TYPE, id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("попытка удаления {} с ID: {}", ENTITY_TYPE, id);
        try {
            repository.deleteById(id);
            log.info("{} с ID: {} удален", ENTITY_TYPE, id);

        } catch (Exception e) {
            log.error("Ошибка при удалении {}: {}", ENTITY_TYPE, e.getMessage());
            throw e;
        }
    }
}
