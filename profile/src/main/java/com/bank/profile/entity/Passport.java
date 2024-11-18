package com.bank.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passport", schema = "profile")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer series;
    private Long number;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_place", nullable = false)
    private String birthPlace;

    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "division_code", nullable = false)
    private Integer divisionCode;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;
}