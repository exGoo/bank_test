package com.bank.antifraud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "suspicious_card_transfer")
public class SuspiciousCardTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_tranfser_id", nullable = false, unique = true)
    private Long cardTranfserId;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @Column(name = "is_suspicious", nullable = false)
    private Boolean isSuspicious;

    @Column(name = "blocker_reason")
    private String blockerReason;

    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;
}