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
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchDto implements Auditable<Long> {

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

    @JsonIgnore
    @Override
    public Long getEntityId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getEntityName() {
        return "Branch";
    }
}
