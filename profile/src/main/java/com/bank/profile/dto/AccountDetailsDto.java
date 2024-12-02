package com.bank.profile.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountDetailsDto {
    private Long id;
    private Long accountId;
    private Long profileId;
}
