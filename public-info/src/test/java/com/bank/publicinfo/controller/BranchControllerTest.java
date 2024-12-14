package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final Long TEST_ID = 1L;

    private static final BranchDto DTO = new BranchDto(TEST_ID, "Moscow");

    private static final BranchDto CREATED_BRANCH = BranchDto.builder()
            .id(1L)
            .address("MyStreet")
            .phoneNumber(12134L)
            .city("Moscow")
            .build();

    private static final List<BranchDto> DTO_LIST = Arrays.asList(
            new BranchDto(2L, "Minsk"),
            new BranchDto(3L, "Minsk")
    );

    @Test
    void getBranchById() throws Exception {
        when(branchService.findById(TEST_ID)).thenReturn(DTO);
        mockMvc.perform(get("/branches/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Moscow"));
        verify(branchService, times(1)).findById(anyLong());
    }

    @Test
    void getAllBranches() throws Exception {
        when(branchService.findAllWithATMs()).thenReturn(DTO_LIST);
        mockMvc.perform(get("/branches"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Minsk"));
        verify(branchService, times(1)).findAllWithATMs();
    }

    @Test
    void getBranchesByCity() throws Exception {
        when(branchService.findByCity("Minsk")).thenReturn(DTO_LIST);
        mockMvc.perform(get("/branches/city/{city}", "Minsk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value("Minsk"));
        verify(branchService, times(1)).findByCity("Minsk");
    }

    @Test
    void addBankDetails() throws Exception {
        when(branchService.addBranch(any(BranchDto.class))).thenReturn(CREATED_BRANCH);
        String branchDtoJson = objectMapper.writeValueAsString(CREATED_BRANCH);
        mockMvc.perform(post("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CREATED_BRANCH.getId()));
        verify(branchService, times(1)).addBranch(any(BranchDto.class));
    }

    @Test
    void updateBranch() throws Exception {
        BranchDto updatedDto = CREATED_BRANCH.toBuilder().city("Rome").build();
        when(branchService.updateBranch(anyLong(), any(BranchDto.class))).thenReturn(updatedDto);
        String updatedBranchDtoJson = objectMapper.writeValueAsString(updatedDto);
        mockMvc.perform(patch("/branches/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBranchDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(updatedDto.getCity()));
        verify(branchService, times(1)).updateBranch(anyLong(), any(BranchDto.class));
    }

    @Test
    void deleteBranch() throws Exception {
        doNothing().when(branchService).deleteBranchById(TEST_ID);
        mockMvc.perform(delete("/branches/{id}", TEST_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(branchService, times(1)).deleteBranchById(anyLong());
    }
}
