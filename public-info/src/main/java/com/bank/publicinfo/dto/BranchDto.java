package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {

    private Long id;

    @NotNull
    private String address;

    @NotNull
    private Long phoneNumber;

    @NotNull
    private String city;

    @NotNull
    private LocalTime startOfWork;

    @NotNull
    private LocalTime endOfWork;

    private Set<Long> atmsIds;
}
