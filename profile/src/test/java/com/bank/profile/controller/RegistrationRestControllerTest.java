package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
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
class RegistrationRestControllerTest {
    RegistrationDto DTO = RegistrationDto.builder()
            .id(1L)
            .build();

    @Mock
    RegistrationService service;
    @InjectMocks
    RegistrationRestController controller;

    @Test
    void save() {
        when(service.save(any(RegistrationDto.class))).thenReturn(DTO);

        ResponseEntity<String> result = controller
                .save(DTO);

        assertNotNull(result);
        assertEquals("Registration saved", result.getBody());
    }

    @Test
    void getById() {
        when(service.findById(1L)).thenReturn(DTO);

        ResponseEntity<RegistrationDto> result = controller.getById(1L);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void getAll() {
        when(service.findAll()).thenReturn(List.of(DTO));
        ResponseEntity<List<RegistrationDto>> result = controller.getAll();
        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void update() {
        when(service.update(1L, DTO)).thenReturn(DTO);

        ResponseEntity<String> result = controller.update(1L, DTO);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void deleteById() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<String> result = controller.deleteById(1L);

        assertEquals("Deleted Registration",result.getBody());
    }
}