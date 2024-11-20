package com.bank.profile.dto;

import com.bank.profile.entity.AccountDetails;
import com.bank.profile.entity.ActualRegistration;
import com.bank.profile.entity.Passport;
import lombok.Data;

import javax.persistence.*;
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
