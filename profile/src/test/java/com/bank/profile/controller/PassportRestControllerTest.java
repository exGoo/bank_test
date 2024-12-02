package com.bank.profile.controller;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.service.PassportService;
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
class PassportRestControllerTest {
    PassportDto DTO = PassportDto.builder().id(1L).build();

    @Mock
    PassportService service;
    @InjectMocks
    PassportRestController controller;

    @Test
    void save() {
        when(service.save(any(PassportDto.class))).thenReturn(DTO);

        ResponseEntity<String> result = controller
                .save(DTO);

        assertNotNull(result);
        assertEquals("Passport saved", result.getBody());
    }

    @Test
    void getRegistrationById() {
        when(service.findById(1L)).thenReturn(DTO);

        ResponseEntity<PassportDto> result = controller.getRegistrationById(1L);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void getAllRegistrations() {

        when(service.findAll()).thenReturn(List.of(DTO));
        ResponseEntity<List<PassportDto>> result = controller.getAllRegistrations();
        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void update() {
        when(service.update(1L, DTO)).thenReturn(DTO);

        ResponseEntity<String> result = controller.update(1L, DTO);

        assertEquals(HttpStatus.OK,result.getStatusCode());
    }

    @Test
    void deleteRegistrationById() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<String> result = controller.deleteRegistrationById(1L);

        assertEquals("Passport deleted",result.getBody());
    }
}