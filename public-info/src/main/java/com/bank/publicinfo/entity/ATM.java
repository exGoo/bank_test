package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "atm")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 370)
    private String address;

    @Column
    private LocalTime startOfWork;

    @Column
    private LocalTime endOfWork;

    @Column(nullable = false)
    private boolean allHours;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;



}
