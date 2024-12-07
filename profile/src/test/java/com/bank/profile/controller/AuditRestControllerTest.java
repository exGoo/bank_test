package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditRestControllerTest {
    AuditDto DTO = AuditDto.builder()
            .id(1L)
            .build();

    @Mock
    AuditService service;
    @InjectMocks
    AuditRestController controller;

    @Test
    void save() {
        when(service.save(any(AuditDto.class))).thenReturn(DTO);

        ResponseEntity<AuditDto> result = controller
                .save(DTO);

        assertNotNull(result);
        assertEquals(DTO, result.getBody());
    }

    @Test
    void getById() {
        when(service.findById(1L)).thenReturn(DTO);

        ResponseEntity<AuditDto> result = controller.getById(1L);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void getAll() {
        when(service.findAll()).thenReturn(List.of(DTO));

        ResponseEntity<List<AuditDto>> result = controller.getAll();

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void update() {
        doNothing().when(service).update(1L, DTO);

        ResponseEntity<String> result = controller.update(1L, DTO);

        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals("Audit updated", result.getBody());
    }

    @Test
    void deleteById() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<String> result = controller.deleteById(1L);

        assertEquals("Audit deleted",result.getBody());
    }
}