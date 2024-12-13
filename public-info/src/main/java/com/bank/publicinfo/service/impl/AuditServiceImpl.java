package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.exception.DataValidationException;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.utils.Admin;
import com.bank.publicinfo.utils.Auditable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    private final ObjectMapper objectMapper;

    private final Admin admin;

    @Override
    public void saveNewAudit(Auditable<?> entity) {

        try {
            log.info("Попытка создания и сохранения нового аудита");
            Audit newAudit = Audit.builder()
                    .entityType(entity.getEntityName())
                    .operationType("CREATE")
                    .createdBy(admin.getUsername())
                    .createdAt(LocalDateTime.now())
                    .entityJson(objectMapper.writeValueAsString(entity))
                    .build();
            auditRepository.save(newAudit);
            log.info("Новый аудит успешно создан и сохранен");
        } catch (JsonProcessingException e) {
            log.error("Ошибка при преобразовании JSON");
            throw new RuntimeException("Please check the correctness of JSON");

        } catch (RuntimeException e) {
            log.error("Получены некорректные данные для аудита");
            throw new DataValidationException("Please check the correctness of the entered data");
        }
    }

    @Override
    public void refreshAudit(Auditable<?> newEntity) {

        try {
            log.info("Попытка поиска аудита по id для получения данных для новой записи");
            Audit lastAudit = findLastAudit(newEntity.getEntityId().toString(), newEntity.getEntityName());
            log.info("Аудит успешно найден");
            Audit updatableAudit = Audit.builder()
                    .entityType(newEntity.getEntityName())
                    .operationType("UPDATE")
                    .createdBy(lastAudit.getCreatedBy())
                    .modifiedBy(admin.getUsername())
                    .createdAt(lastAudit.getCreatedAt())
                    .modifiedAt(LocalDateTime.now())
                    .newEntityJson(objectMapper.writeValueAsString(newEntity))
                    .entityJson(lastAudit.getEntityJson())
                    .build();
            log.info("Попытка создания и сохранения нового аудита для операции update");
            auditRepository.save(updatableAudit);
            log.info("Новый аудит успешно создан и сохранен");
        } catch (JsonProcessingException e) {
            log.error("Ошибка при преобразовании JSON");
            throw new RuntimeException("Please check the correctness of JSON");

        } catch (NotFoundException e) {
            log.error("Аудит для данного id не найден");
            throw new EntityNotFoundException("Audit with this id not found");

        } catch (RuntimeException e) {
            log.error("Получены некорректные данные для аудита");
            throw new DataValidationException("Please check the correctness of the entered data");

        }
    }

    public Audit findLastAudit(String id, String entityType) {
        return auditRepository.findByEntityJsonId(id, entityType)
                .orElseThrow(() -> new NotFoundException("Audit with this id not found"));
    }
}
