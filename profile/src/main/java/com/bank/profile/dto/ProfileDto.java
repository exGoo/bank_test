package com.bank.profile.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;
@Data
public class ProfileDto {

    private Long id;
    private Long phoneNumber;
    @Size(max = 264,message ="до 264")
    private String email;

    @Size(max = 370, message ="до 370")
    private String nameOnCard;

    private Long inn;
    private Long snils;
    private Long passportId;
    private Long actualRegistrationId;
    private List<Long> accountDetailsId;
}
