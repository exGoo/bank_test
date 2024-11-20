package com.bank.profile.dto;

import com.bank.profile.entity.Registration;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Data
public class PassportDto {
    private Long id;
    private Integer series;
    private Long number;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private LocalDate birthDate;
    private String birthPlace;
    private String issuedBy;
    private LocalDate dateOfIssue;
    private Integer divisionCode;
    private LocalDate expirationDate;
    private RegistrationDto registration;
}
