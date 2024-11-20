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
    private PassportDto passport;
    private ActualRegistrationDto actualRegistration;
    private List<AccountDetailsDto> accountDetails;
}
