package com.bank.profile.aspect;

import com.bank.profile.annotation.AuditSave;
import com.bank.profile.annotation.AuditUpdate;
import com.bank.profile.dto.mapper.AuditMapper;
import com.bank.profile.entity.Audit;
import com.bank.profile.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditAspectTest {
    @Mock
    AuditRepository repository;
    @Mock
    AuditMapper mapper;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    AuditAspect aspect;

    @Test
    void saveAspect() throws JsonProcessingException {
        Object result = new TestEntity(1L, "TestEntity");
        AuditSave annotation = mock(AuditSave.class);
        when(annotation.entityType()).thenReturn("testEntity");

        String serializedEntity = "{\"id\":1,\"name\":\"TestEntity\"}";
        when(objectMapper.writeValueAsString(result)).thenReturn(serializedEntity);

        Audit savedAudit = Audit.builder()
                .id(1L)
                .entityType("testEntity")
                .operationType("create")
                .createdBy("user")
                .createdAt(OffsetDateTime.now())
                .entityJson(serializedEntity)
                .build();

        when(repository.save(any(Audit.class))).thenReturn(savedAudit);

        // Вызов метода
        aspect.saveAspect(annotation, result);

        // Проверки
        verify(repository, times(1)).save(any(Audit.class));
        verify(objectMapper, times(1)).writeValueAsString(result);
    }

    @Test
    void testSaveAspectSerializationError() throws JsonProcessingException {
        // Подготовка данных
        Object result = new TestEntity(1L, "TestEntity");
        AuditSave annotation = mock(AuditSave.class);
        when(annotation.entityType()).thenReturn("testEntity");

        when(objectMapper.writeValueAsString(result)).thenThrow(new JsonProcessingException("Serialization error") {});

        // Вызов метода и проверка исключения
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                aspect.saveAspect(annotation, result));

        assertEquals("Error serializing entity", exception.getMessage());
        verify(repository, never()).save(any(Audit.class));
    }

    @Test
    void updateAspect() throws JsonProcessingException {
        TestEntity result = new TestEntity(1L, "testEntity");
        AuditUpdate annotation = mock(AuditUpdate.class);
        when(annotation.entityType()).thenReturn("testEntity");

        Audit existingAudit = Audit.builder()
                .id(1L)
                .entityType("testEntity")
                .operationType("create")
                .createdBy("user")
                .createdAt(OffsetDateTime.now())
                .entityJson("{\"id\":1,\"name\":\"TestEntity\"}")
                .build();

        when(repository.findCreateAuditRecordByEntityAndId("testEntity", 1L))
                .thenReturn(Optional.of(existingAudit));
        when(objectMapper.writeValueAsString(result))
                .thenReturn("{\"id\":1,\"name\":\"testEntity\"}");

        Audit updatedAudit = Audit.builder()
                .id(2L)
                .entityType("testEntity")
                .operationType("update")
                .createdBy("user")
                .createdAt(existingAudit.getCreatedAt())
                .modifiedAt(OffsetDateTime.now())
                .modifiedBy("user")
                .entityJson(existingAudit.getEntityJson())
                .newEntityJson("{\"id\":1,\"name\":\"testEntity\"}")
                .build();

        when(repository.save(any(Audit.class))).thenReturn(updatedAudit);

        // Вызов метода
        aspect.updateAspect(annotation, result);

        // Проверки
        verify(repository, times(1)).findCreateAuditRecordByEntityAndId("testEntity", 1L);
        verify(repository, times(1)).save(any(Audit.class));
        verify(objectMapper, times(1)).writeValueAsString(result);
    }


    @Data
    @AllArgsConstructor
    private class TestEntity {
        private long id;
        private String name;
    }
}
