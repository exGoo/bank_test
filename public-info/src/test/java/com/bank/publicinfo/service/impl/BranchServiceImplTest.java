package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
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

    private Branch branch;

    private BranchDto branchDto;

    @BeforeEach
    public void setUp() {
        branch = new Branch();
        branch.setId(1L);
        branch.setCity("Moscow");
        branchDto = new BranchDto();
        branchDto.setId(1L);
        branchDto.setCity("Moscow");
    }

    @Test
    void findByIdSucces() {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(branchMapper.toDto(branch)).thenReturn(branchDto);
        BranchDto result = branchService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(branchRepository, times(1)).findById(1L);
        verify(branchMapper, times(1)).toDto(branch);
    }

    @Test
    void findByIdFailure() {
        Long id = 1L;
        when(branchRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            branchService.findById(id);
        });
        assertEquals("Branch not found with id " + id, exception.getMessage());
        verify(branchRepository).findById(id);
    }

    @Test
    void findAllWithATMs() {
        List<Branch> detailsList = List.of(branch);
        when(branchRepository.findAll()).thenReturn(detailsList);
        when(branchMapper.toDto(branch)).thenReturn(branchDto);
        List<BranchDto> result = branchService.findAllWithATMs();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(branchRepository, times(1)).findAll();
        verify(branchMapper, times(1)).toDto(branch);
    }

    @Test
    void findByCitySucces() {
        String city = "Moscow";
        List<Branch> branchList = Arrays.asList(branch);
        BranchDto branchDto = new BranchDto(1L, city);
        when(branchRepository.findByCityWithAtms(city)).thenReturn(branchList);
        when(branchMapper.toDto(branch)).thenReturn(branchDto);
        List<BranchDto> result = branchService.findByCity(city);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(branchDto, result.get(0));
    }

    @Test
    void findByCityEmptyListTest() {
        String city = "UnknownCity";
        List<Branch> emptyList = new ArrayList<>();
        when(branchRepository.findByCityWithAtms(city)).thenReturn(emptyList);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            branchService.findByCity(city);
        });
        assertEquals("City not found " + city, exception.getMessage());
    }

    @Test
    void findByCityFailure() {
        String city = "New York";
        when(branchRepository.findByCityWithAtms(city)).thenThrow(new RuntimeException("Database error"));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            branchService.findByCity(city);
        });
        assertEquals("City not found New York", exception.getMessage());
    }

    @Test
    void addBranch() {
        when(branchMapper.toModel(branchDto)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(branch);
        when(branchMapper.toDto(branch)).thenReturn(branchDto);
        BranchDto result = branchService.addBranch(branchDto);
        assertNotNull(result);
        verify(branchRepository, times(1)).save(branch);
        verify(branchMapper, times(1)).toModel(branchDto);
        verify(branchMapper, times(1)).toDto(branch);
    }

    @Test
    void deleteBranchByIdSucces() {
        doNothing().when(branchRepository).deleteById(1L);
        branchService.deleteBranchById(1L);
        verify(branchRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBranchByIdFailure() {
        doThrow(new EntityNotFoundException("Branch not found with id 1")).when(branchRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> branchService.deleteBranchById(1L));
        verify(branchRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateBranchSucces() {
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);
        when(branchMapper.toDto(branch)).thenReturn(branchDto);
        BranchDto result = branchService.updateBranch(1L, branchDto);
        assertNotNull(result);
        verify(branchRepository, times(1)).findById(1L);
        verify(branchRepository, times(1)).save(any(Branch.class));
        verify(branchMapper, times(1)).toDto(branch);
    }

    @Test
    void updateBranchFailure() {
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> branchService.updateBranch(1L, branchDto));
        verify(branchRepository, times(1)).findById(1L);
    }
}
