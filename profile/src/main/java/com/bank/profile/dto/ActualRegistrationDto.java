package com.bank.profile.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ActualRegistrationDto {
    private Long id;
    @Size(max = 40, message = "до 40")
    private String country;

    @Size(max = 160, message = "до 160")
    private String region;

    @Size(max = 160, message = "до 160")
    private String city;

    @Size(max = 160, message = "до 160")
    private String district;

    @Size(max = 230, message = "до 230")
    private String locality;

    @Size(max = 230, message = "до 230")
    private String street;

    @Size(max = 20, message = "до 20")
    private String houseNumber;

    @Size(max = 20, message = "до 20")
    private String houseBlock;

    @Size(max = 40, message = "до 40")
    private String flatNumber;

    private Long index;
}
