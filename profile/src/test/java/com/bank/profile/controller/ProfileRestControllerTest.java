package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
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
class ProfileRestControllerTest {
    ProfileDto DTO = ProfileDto.builder().id(1L).build();

    @Mock
    ProfileService service;
    @InjectMocks
    ProfileRestController controller;

    @Test
    void save() {
        when(service.save(any(ProfileDto.class))).thenReturn(DTO);

        ResponseEntity<String> result = controller
                .save(DTO);

        assertNotNull(result);
        assertEquals("Profile saved", result.getBody());
    }

    @Test
    void getById() {
        when(service.findById(1L)).thenReturn(DTO);

        ResponseEntity<ProfileDto> result = controller.getRegistrationById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAll() {
        when(service.findAll()).thenReturn(List.of(DTO));
        ResponseEntity<List<ProfileDto>> result = controller.getAllRegistrations();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void update() {
        when(service.update(1L, DTO)).thenReturn(DTO);

        ResponseEntity<String> result = controller.update(1L, DTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteById() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<String> result = controller.deleteRegistrationById(1L);

        assertEquals("registration deleted", result.getBody());
    }
}