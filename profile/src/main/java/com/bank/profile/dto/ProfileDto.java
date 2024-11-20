package com.bank.profile.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProfileDto {

    private Long id;
    private Long phoneNumber;
    private String email;
    private String nameOnCard;
    private Long inn;
    private Long snils;
    private Long passportId;
    private Long actualRegistrationId;
    private List<Long> accountDetailsId;
}
