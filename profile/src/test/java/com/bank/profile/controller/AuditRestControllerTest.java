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
        doNothing().when(service).save(any(AuditDto.class));

        ResponseEntity<String> result = controller
                .save(DTO);

        assertNotNull(result);
        assertEquals("Audit saved", result.getBody());
    }

    @Test
    void getRegistrationById() {
        when(service.findById(1L)).thenReturn(DTO);

        ResponseEntity<AuditDto> result = controller.getRegistrationById(1L);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void getAllRegistrations() {
        when(service.findAll()).thenReturn(List.of(DTO));

        ResponseEntity<List<AuditDto>> result = controller.getAllRegistrations();

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
    void deleteRegistrationById() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<String> result = controller.deleteRegistrationById(1L);

        assertEquals("Audit deleted",result.getBody());
    }
}