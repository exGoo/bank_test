package com.bank.profile.service.impl;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.mapper.ProfileMapper;
import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import com.bank.profile.entity.Profile;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления профилями пользователей.
 * Предоставляет операции для создания, обновления, получения и удаления профилей.
 */
@Service
@Transactional
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private static final String ENTITY_TYPE = "profile";

    ProfileRepository repository;
    ProfileMapper mapper;
    PassportRepository passportRepository;
    AccountDetailsRepository accountDetailsRepository;
    ActualRegistrationRepository actualRegistrationRepository;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param repository                   репозиторий для управления профилями
     * @param mapper                       маппер для преобразования между DTO и сущностями
     * @param passportRepository           репозиторий для управления паспортами
     * @param accountDetailsRepository     репозиторий для управления учетными записями
     * @param actualRegistrationRepository репозиторий для управления регистрационными данными
     */
    @Autowired
    public ProfileServiceImpl(ProfileRepository repository, ProfileMapper mapper,
                              PassportRepository passportRepository,
                              AccountDetailsRepository accountDetailsRepository,
                              ActualRegistrationRepository actualRegistrationRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.passportRepository = passportRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.actualRegistrationRepository = actualRegistrationRepository;

    }

    /**
     * Сохраняет новый профиль.
     *
     * @param profile DTO объекта профиля, который необходимо сохранить
     * @return сохраненный объект профиля в виде DTO
     * AuditSave Аннотация для маркировки методов, которые должны быть аудированы
     * @throws EntityNotFoundException если связанный паспорт или регистрационные данные не найдены
     */
    @Override
    @AuditSave(entityType = ENTITY_TYPE)
    public ProfileDto save(ProfileDto profile) {
        log.info("попытка сохранить {} : {}", ENTITY_TYPE, profile);
        Profile newprofile = mapper.toEntity(profile);
        Passport passport = passportRepository.findById(profile.getPassportId()).orElseThrow(
                () -> new EntityNotFoundException("Passport not found with ID: " + profile.getPassportId()));
        ActualRegistration actualRegistration = actualRegistrationRepository
                .findById(profile.getActualRegistrationId()).orElseThrow(
                        () -> new EntityNotFoundException("ActualRegistration not found with ID: "
                                + profile.getActualRegistrationId()));
        List<AccountDetails> accountDetails = accountDetailsRepository.findAllById(profile.getAccountDetailsId());
        newprofile.setAccountDetails(accountDetails);
        newprofile.setPassport(passport);
        newprofile.setActualRegistration(actualRegistration);
        Profile result = repository.save(newprofile);
        log.info("{} сохранен с ID: {}", ENTITY_TYPE, result.getId());
        return mapper.toDto(result);
    }

    /**
     * Возвращает список всех профилей.
     *
     * @return список всех профилей в виде DTO
     */
    @Override
    public List<ProfileDto> findAll() {
        log.info("Попытка получить список {}", ENTITY_TYPE);
        List<Profile> result = repository.findAll();
        log.info("Найдено {} записей {}", result.size(), ENTITY_TYPE);
        return mapper.toListDto(result);
    }

    /**
     * Ищет профиль по его идентификатору.
     *
     * @param id идентификатор профиля
     * @return найденный профиль в виде DTO
     * @throws EntityNotFoundException если профиль с указанным идентификатором не найден
     */
    @Override
    public ProfileDto findById(Long id) {
        log.info("Попытка получить {} с ID: {}", ENTITY_TYPE, id);
        ProfileDto result = mapper.toDto(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("profile not found with ID: " + id)));
        log.info("{} успешно получена: {}", ENTITY_TYPE, result);
        return result;
    }

    /**
     * Обновляет существующий профиль.
     *
     * @param id      идентификатор профиля для обновления
     * @param profile DTO объекта профиля с новыми данными
     * @return обновленный профиль в виде DTO
     * AuditUpdate Аннотация для маркировки методов, которые должны быть аудированы
     * @throws EntityNotFoundException если профиль, паспорт или регистрационные данные не найдены
     */
    @Override
    @AuditUpdate(entityType = ENTITY_TYPE)
    public ProfileDto update(Long id, ProfileDto profile) {
        log.info("Попытка обновить {} с ID: {}, новые данные: {}", ENTITY_TYPE, id, profile);
        Profile oldProfile = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("profile not found with ID: " + id));
        Passport passport = oldProfile.getPassport();
        if (profile.getPassportId() != null) {
            passport = passportRepository.findById(profile.getPassportId()).orElseThrow(
                    () -> new EntityNotFoundException("Passport not found with ID: " + profile.getPassportId()));
        }
        ActualRegistration actualRegistration = oldProfile.getActualRegistration();
        if (profile.getActualRegistrationId() != null) {
            actualRegistration = actualRegistrationRepository.findById(profile.getActualRegistrationId()).orElseThrow(
                    () -> new EntityNotFoundException("ActualRegistration not found with ID: " + profile.getActualRegistrationId()));
        }
        List<AccountDetails> accountDetails = oldProfile.getAccountDetails();
        if (profile.getAccountDetailsId() != null) {
            accountDetails = profile.getAccountDetailsId().isEmpty()
                    ? new ArrayList<>()
                    : accountDetailsRepository.findAllById(profile.getAccountDetailsId());
        }
        mapper.updateEntityFromDto(oldProfile, profile);
        oldProfile.setPassport(passport);
        oldProfile.setActualRegistration(actualRegistration);
        oldProfile.setAccountDetails(accountDetails);
        Profile result = repository.save(oldProfile);
        log.info("{} с ID: {} успешно обновлен", ENTITY_TYPE, id);
        return mapper.toDto(result);
    }

    /**
     * Удаляет профиль по его идентификатору.
     *
     * @param id идентификатор профиля для удаления
     * @throws EntityNotFoundException если профиль с указанным идентификатором не найден
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        log.info("{} с ID: {} удален", ENTITY_TYPE, id);
    }
}
