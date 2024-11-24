package com.bank.publicinfo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDto {
    private Long id;
    private byte[] photo;
    private Long bankDetailsId;
}
