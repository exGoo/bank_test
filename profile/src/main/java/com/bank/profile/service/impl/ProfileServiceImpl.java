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

    ProfileRepository repository;
    ProfileMapper mapper;
    PassportRepository passportRepository;
    AccountDetailsRepository accountDetailsRepository;
    ActualRegistrationRepository actualRegistrationRepository;
    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param repository                 репозиторий для управления профилями
     * @param mapper                     маппер для преобразования между DTO и сущностями
     * @param passportRepository         репозиторий для управления паспортами
     * @param accountDetailsRepository   репозиторий для управления учетными записями
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
     * @AuditSave Аннотация для маркировки методов, которые должны быть аудированы
     * @throws EntityNotFoundException если связанный паспорт или регистрационные данные не найдены
     */
    @Override
    @AuditSave(entityType = "profile")
    public ProfileDto save(ProfileDto profile) {
        log.info("попытка сохранить profile : {}", profile);
        try {
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
            log.info("profile сохранен с ID: {}", result.getId());
            return mapper.toDto(result);

        } catch (EntityNotFoundException e) {
            log.error("Ошибка при создании profile: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Возвращает список всех профилей.
     *
     * @return список всех профилей в виде DTO
     */
    @Override
    public List<ProfileDto> findAll() {
        log.info("Попытка получить список profile");
        try {
            List<Profile> result = repository.findAll();
            log.info("Найдено {} записей profile", result.size());
            return mapper.toListDto(result);
        } catch (Exception e) {
            log.error("Ошибка при получении списка profile записей: {}", e.getMessage());
            throw e;
        }
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
        log.info("Попытка получить profile с ID: {}", id);
        try {
            ProfileDto result = mapper.toDto(repository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("profile not found with ID: " + id)));
            log.info("profile успешно получена: {}", result);
            return result;
        } catch (EntityNotFoundException e) {
            log.error("profile с ID: {} не найден:{}", id, e.getMessage());
            throw e;
        }
    }
    /**
     * Обновляет существующий профиль.
     *
     * @param id      идентификатор профиля для обновления
     * @param profile DTO объекта профиля с новыми данными
     * @return обновленный профиль в виде DTO
     *  @AuditUpdate Аннотация для маркировки методов, которые должны быть аудированы
     * @throws EntityNotFoundException если профиль, паспорт или регистрационные данные не найдены
     */
    @Override
    @AuditUpdate(entityType = "profile")
    public ProfileDto update(Long id, ProfileDto profile) {
        log.info("Попытка обновить profile с ID: {}, новые данные: {}", id, profile);
        try {
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
            log.info("profile с ID: {} успешно обновлен", id);
            return mapper.toDto(result);

        } catch (EntityNotFoundException e) {
            log.error("ошибка при обновлении profile с ID: {} message: {}", id, e.getMessage());
            throw e;
        }
    }
    /**
     * Удаляет профиль по его идентификатору.
     *
     * @param id идентификатор профиля для удаления
     * @throws EntityNotFoundException если профиль с указанным идентификатором не найден
     */
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            log.info("profile с ID: {} удален", id);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка при удалении profile: {}", e.getMessage());
            throw e;
        }
    }
}
