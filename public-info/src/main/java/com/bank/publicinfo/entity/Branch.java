package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "branch")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 370)
    private String address;

    @Column(nullable = false, unique = true)
    private Long phoneNumber;

    @Column(nullable = false, length = 250)
    private String city;

    @Column(nullable = false)
    private LocalTime startOfWork;

    @Column(nullable = false)
    private LocalTime endOfWork;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ATM> atms;


}
