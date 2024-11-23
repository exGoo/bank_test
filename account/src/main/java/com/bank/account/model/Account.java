package com.bank.account.model;

import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_details", schema = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long passportId;
    @Column(unique = true)
    private Long accountNumber;
    @Column(unique = true)
    private Long bankDetailsId;
    @Column
    private BigDecimal money;
    @Column
    private Boolean negativeBalance;
    @Column
    private Long profileId;
}
