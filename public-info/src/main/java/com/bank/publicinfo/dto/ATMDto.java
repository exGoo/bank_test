package com.bank.publicinfo.dto;

import com.bank.publicinfo.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ATMDto implements Auditable<Long> {

    @NotNull
    private Long id;

    @NotNull
    private String address;

    private LocalTime startOfWork;

    private LocalTime endOfWork;

    @NotNull
    private Boolean allHours;

    private Long branchId;

    @JsonIgnore
    @Override
    public Long getEntityId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getEntityName() {
        return "ATM";
    }
}
