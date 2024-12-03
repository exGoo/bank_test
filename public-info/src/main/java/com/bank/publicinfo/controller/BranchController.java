package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branches")
@Tag(name = "Branches", description = "API для управления отделениями банка")
public class BranchController {

    private BranchService branchService;

    @Autowired
    public void setBranchService(BranchService branchService) {
        this.branchService = branchService;
    }

    @Operation(summary = "Получить отделение по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение отделения",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchDto.class))),
            @ApiResponse(responseCode = "404", description = "Отделение не найдено")})
    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) {
        BranchDto branchDto = branchService.findById(id);
        return new ResponseEntity<>(branchDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все отделения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка отделений",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchDto.class)))})
    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranch() {
        List<BranchDto> branchDto = branchService.findAllWithATMs();
        return new ResponseEntity<>(branchDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить все отделения в определенном городе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка отделений",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchDto.class)))})
    @GetMapping("/city/{city}")
    public ResponseEntity<List<BranchDto>> getBranchByCity(@PathVariable String city) {
        List<BranchDto> branchDtos = branchService.findByCity(city);
        return new ResponseEntity<>(branchDtos, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новое отделение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Успешное добавление"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public ResponseEntity<BranchDto> addBranch(@RequestBody BranchDto branchDto) {
        BranchDto createdBankDetails = branchService.addBranch(branchDto);
        return new ResponseEntity<>(createdBankDetails, HttpStatus.CREATED);
    }

    @Operation(summary = "Обновить отделение по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление"),
            @ApiResponse(responseCode = "404", description = "Отделение не найдено"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @RequestBody BranchDto bankDetailsDto) {
        BranchDto updatedBranch = branchService.updateBranch(id, bankDetailsDto);
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }

    @Operation(summary = "Удалить отделение по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешное удаление"),
            @ApiResponse(responseCode = "404", description = "Отделение не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranchById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
