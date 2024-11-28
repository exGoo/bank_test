package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATMDto {

    @NotNull
    private Long id;

    @NotNull
    private String address;

    private LocalTime startOfWork;

    private LocalTime endOfWork;

    @NotNull
    private Boolean allHours;

    private Long branchId;
    
}
