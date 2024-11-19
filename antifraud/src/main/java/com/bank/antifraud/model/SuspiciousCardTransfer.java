package com.bank.antifraud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "suspicious_card_transfer", schema = "anti_fraud")
public class SuspiciousCardTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_transfer_id", nullable = false, unique = true)
    private Long cardTransferId;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @Column(name = "is_suspicious", nullable = false)
    private Boolean isSuspicious;

    @Column(name = "blocked_reason")
    private String blockerReason;

    @Column(name = "suspicious_reason", nullable = false)
    private String suspiciousReason;

}

