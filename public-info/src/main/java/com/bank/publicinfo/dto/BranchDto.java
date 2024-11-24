package com.bank.publicinfo.dto;

import lombok.*;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
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
    private Set<Long> atmsIds;
}
