package com.bank.publicinfo.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long bik;

    @Column(nullable = false, unique = true)
    private Long inn;

    @Column(nullable = false, unique = true)
    private Long kpp;

    @Column(nullable = false, unique = true)
    private int corAccount;

    @Column(nullable = false, length = 180)
    private String city;

    @Column(nullable = false, length = 15)
    private String jointStockCompany;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<License> licenses;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;


}
