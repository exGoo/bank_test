package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.ATM;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Long id;
    private String address;
    private Long phoneNumber;
    private String city;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private List<ATM> atms;
}
