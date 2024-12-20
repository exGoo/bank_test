package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVAID_JSON_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_INVALID_JSON;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_BRANCHES;
import static com.bank.publicinfo.utils.TestsUtils.toJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BranchController.class)
class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    private static final String branchDtoJson = toJson(TEST_BRANCH_DTO);

    private static final String listBranchDtoJson = toJson(TEST_LIST_BRANCHES);

    @Test
    void getBranchById() throws Exception {
        when(branchService.findById(TEST_ID_1)).thenReturn(TEST_BRANCH_DTO);
        mockMvc.perform(get("/branches/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(branchDtoJson));
        verify(branchService).findById(anyLong());
    }

    @Test
    void getBranchByIdWhenNotFound() throws Exception {
        when(branchService.findById(TEST_ID_1)).thenThrow(new EntityNotFoundException("Branch not found"));
        mockMvc.perform(get("/branches/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Branch not found"));
        verify(branchService).findById(anyLong());
    }

    @Test
    void getAllBranches() throws Exception {
        when(branchService.findAllWithATMs()).thenReturn(TEST_LIST_BRANCHES);
        mockMvc.perform(get("/branches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(listBranchDtoJson));
        verify(branchService).findAllWithATMs();
    }

    @Test
    void getAllBranchesWhenNotFound() throws Exception {
        when(branchService.findAllWithATMs()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/branches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(branchService).findAllWithATMs();
    }

    @Test
    void getBranchesByCity() throws Exception {
        when(branchService.findByCity(TEST_CITY_1)).thenReturn(TEST_LIST_BRANCHES);
        mockMvc.perform(get("/branches/city/{city}", TEST_CITY_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value(TEST_CITY_1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value(TEST_CITY_1));
        verify(branchService).findByCity(TEST_CITY_1);
    }

    @Test
    void getBranchesByCityWhenNotFound() throws Exception {
        when(branchService.findByCity(TEST_CITY_1)).thenThrow(new EntityNotFoundException("Branch not found"));
        mockMvc.perform(get("/branches/city/{city}", TEST_CITY_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Branch not found"));
        verify(branchService).findByCity(anyString());
    }

    @Test
    void addBranch() throws Exception {
        when(branchService.addBranch(any(BranchDto.class))).thenReturn(TEST_BRANCH_DTO);
        mockMvc.perform(post("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchDtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(branchDtoJson));
        verify(branchService).addBranch(any(BranchDto.class));
    }

    @Test
    void addBranchWhenRequestBodyInvalid() throws Exception {
        mockMvc.perform(post("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVALID_JSON))
                .andExpect(status().isBadRequest());
        verify(branchService, never()).addBranch(any(BranchDto.class));
    }

    @Test
    void updateBranch() throws Exception {
        when(branchService.updateBranch(anyLong(), any(BranchDto.class))).thenReturn(TEST_BRANCH_DTO);
        mockMvc.perform(patch("/branches/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchDtoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(branchDtoJson));
        verify(branchService).updateBranch(anyLong(), any(BranchDto.class));
    }

    @Test
    void updateBranchWhenNotFound() throws Exception {
        when(branchService.updateBranch(anyLong(), any(BranchDto.class)))
                .thenThrow(new EntityNotFoundException("Branch not found with id " + TEST_ID_1));
        mockMvc.perform(patch("/branches/{id}", TEST_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_INVAID_JSON_2))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Branch not found with id " + TEST_ID_1));
        verify(branchService).updateBranch(anyLong(), any(BranchDto.class));
    }

    @Test
    void deleteBranch() throws Exception {
        doNothing().when(branchService).deleteBranchById(TEST_ID_1);
        mockMvc.perform(delete("/branches/{id}", TEST_ID_1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(branchService).deleteBranchById(anyLong());
    }

    @Test
    void deleteBranchWhenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Branch not found with id "+ TEST_ID_1))
                .when(branchService).deleteBranchById(TEST_ID_1);
        mockMvc.perform(delete("/branches/{id}", TEST_ID_1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Branch not found with id " + TEST_ID_1));
        verify(branchService).deleteBranchById(anyLong());
    }
}
