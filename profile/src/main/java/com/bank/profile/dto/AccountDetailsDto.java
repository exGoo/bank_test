package com.bank.profile.dto;

import com.bank.profile.dto.util.Identifiable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailsDto implements Identifiable {
    private Long id;
    private Long accountId;
    private Long profileId;
}
