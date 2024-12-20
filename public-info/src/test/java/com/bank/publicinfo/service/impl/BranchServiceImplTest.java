package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH_DTO;
import static com.bank.publicinfo.utils.TestsUtils.TEST_BRANCH_DTO_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_CITY_2;
import static com.bank.publicinfo.utils.TestsUtils.TEST_ID_1;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_BRANCHES;
import static com.bank.publicinfo.utils.TestsUtils.TEST_LIST_BRANCHES_2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @InjectMocks
    private BranchServiceImpl branchService;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchMapper branchMapper;

    private static final ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);

    private static final ArgumentCaptor<Branch> branchArgumentCaptor = ArgumentCaptor.forClass(Branch.class);

    private static final ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void findByIdSuccess() {
        when(branchRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_BRANCH));
        when(branchMapper.toDto(TEST_BRANCH)).thenReturn(TEST_BRANCH_DTO);
        BranchDto result = branchService.findById(TEST_ID_1);
        assertNotNull(result);
        assertEquals(TEST_BRANCH_DTO,result);
        verify(branchRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
        verify(branchMapper).toDto(TEST_BRANCH);
    }

    @Test
    void findByIdFailure() {
        when(branchRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            branchService.findById(TEST_ID_1);
        });
        assertTrue(exception.getMessage().contains("Branch not found with id " + TEST_ID_1));
        verify(branchRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void findAllWithATMs() {
        when(branchRepository.findAll()).thenReturn(TEST_LIST_BRANCHES_2);
        when(branchMapper.toDto(TEST_BRANCH)).thenReturn(TEST_BRANCH_DTO);
        when(branchMapper.toDto(TEST_BRANCH_2)).thenReturn(TEST_BRANCH_DTO_2);
        List<BranchDto> result = branchService.findAllWithATMs();
        assertNotNull(result);
        assertEquals(TEST_LIST_BRANCHES,result);
        verify(branchRepository).findAll();
        verify(branchMapper).toDto(TEST_BRANCH);
        verify(branchMapper).toDto(TEST_BRANCH_2);
    }

    @Test
    void findAllElseEmptyList() {
        when(branchRepository.findAll()).thenReturn(Collections.emptyList());
        List<BranchDto> result = branchService.findAllWithATMs();
        assertTrue(result.isEmpty());
    }

    @Test
    void findByCitySuccess() {
        when(branchRepository.findByCityWithAtms(TEST_CITY_1)).thenReturn(TEST_LIST_BRANCHES_2);
        when(branchMapper.toDto(TEST_BRANCH)).thenReturn(TEST_BRANCH_DTO);
        when(branchMapper.toDto(TEST_BRANCH_2)).thenReturn(TEST_BRANCH_DTO_2);
        List<BranchDto> result = branchService.findByCity(TEST_CITY_1);
        assertNotNull(result);
        assertEquals(TEST_LIST_BRANCHES, result);
        verify(branchRepository).findByCityWithAtms(stringArgumentCaptor.capture());
        assertEquals(TEST_CITY_1, stringArgumentCaptor.getValue());
        verify(branchMapper).toDto(TEST_BRANCH);
        verify(branchMapper).toDto(TEST_BRANCH_2);
    }

    @Test
    void findByCityEmptyListTest() {
        when(branchRepository.findByCityWithAtms(TEST_CITY_2)).thenReturn(Collections.emptyList());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            branchService.findByCity(TEST_CITY_2);
        });
        assertTrue(exception.getMessage().contains("City not found " + TEST_CITY_2));
    }

    @Test
    void findByCityFailure() {
        when(branchRepository.findByCityWithAtms(TEST_CITY_2)).thenThrow(new RuntimeException("Database error"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            branchService.findByCity(TEST_CITY_2);
        });
        assertTrue(exception.getMessage().contains("City not found " + TEST_CITY_2));
    }

    @Test
    void addBranch() {
        when(branchMapper.toModel(TEST_BRANCH_DTO)).thenReturn(TEST_BRANCH);
        when(branchRepository.save(TEST_BRANCH)).thenReturn(TEST_BRANCH);
        when(branchMapper.toDto(TEST_BRANCH)).thenReturn(TEST_BRANCH_DTO);
        BranchDto result = branchService.addBranch(TEST_BRANCH_DTO);
        assertNotNull(result);
        verify(branchRepository).save(branchArgumentCaptor.capture());
        assertEquals(TEST_BRANCH, branchArgumentCaptor.getValue());
        verify(branchMapper).toModel(TEST_BRANCH_DTO);
        verify(branchMapper).toDto(TEST_BRANCH);
    }

    @Test
    void deleteBranchByIdSuccess() {
        doNothing().when(branchRepository).deleteById(TEST_ID_1);
        branchService.deleteBranchById(TEST_ID_1);
        verify(branchRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void deleteBranchByIdFailure() {
        doThrow(new EntityNotFoundException("Branch not found with id " + TEST_ID_1))
                .when(branchRepository).deleteById(TEST_ID_1);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> branchService.deleteBranchById(TEST_ID_1));
        assertTrue(exception.getMessage().contains("Branch not found with id " + TEST_ID_1));
        verify(branchRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }

    @Test
    void updateBranchSuccess() {
        when(branchRepository.findById(TEST_ID_1)).thenReturn(Optional.of(TEST_BRANCH));
        when(branchRepository.save(any(Branch.class))).thenReturn(TEST_BRANCH);
        when(branchMapper.toDto(TEST_BRANCH)).thenReturn(TEST_BRANCH_DTO);
        BranchDto result = branchService.updateBranch(TEST_ID_1, TEST_BRANCH_DTO);
        assertNotNull(result);
        verify(branchRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
        verify(branchRepository).save(branchArgumentCaptor.capture());
        assertEquals(TEST_BRANCH, branchArgumentCaptor.getValue());
        verify(branchMapper).toDto(TEST_BRANCH);
    }

    @Test
    void updateBranchFailure() {
        when(branchRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> branchService.updateBranch(TEST_ID_1, TEST_BRANCH_DTO));
        assertTrue(exception.getMessage().contains("Branch not found with id " + TEST_ID_1));
        verify(branchRepository).findById(longArgumentCaptor.capture());
        assertEquals(TEST_ID_1, longArgumentCaptor.getValue());
    }
}
