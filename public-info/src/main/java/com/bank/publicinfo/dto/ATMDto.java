package com.bank.publicinfo.dto;

import lombok.*;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATMDto {
    private Long id;
    private String address;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private Boolean allHours;
    private Long branchId;
    
}
