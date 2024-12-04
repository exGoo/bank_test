package com.bank.profile.dto;

import com.bank.profile.dto.util.Identifiable;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@Builder
public class PassportDto implements Identifiable {
    private Long id;
    private Integer series;
    private Long number;
    @Size(max = 255)
    private String lastName;
    @Size(max = 255,message = "до 255 ")
    private String firstName;
    @Size(max = 255, message = "до 255 ")
    private String middleName;
    @Size(max = 3,message = "от 0 до 3")
    private String gender;
    private LocalDate birthDate;
    @Size(max = 480, message = "до 480 ")
    private String birthPlace;
    private String issuedBy;
    private LocalDate dateOfIssue;
    private Integer divisionCode;
    private LocalDate expirationDate;
    private Long registrationId;
}
