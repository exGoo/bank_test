package com.bank.profile.dto;

import com.bank.profile.entity.Profile;
import lombok.Data;

@Data
public class AccountDetailsDto {
    private Long id;
    private Long accountId;
    private Long profileId;
}
