package com.bank.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile" , schema = "profile")
public class Profile {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "phone_number", nullable = false)
   private Long phoneNumber;

   private String email;
   private String nameOnCard;
   private Long inn;
   private Long snils;

   @OneToOne()
   @JoinColumn(name = "passport_id", nullable = false)
   private Passport passport;

   @OneToOne()
   @JoinColumn(name = "actual_registration_id")
   private ActualRegistration actualRegistration;

   @OneToMany(mappedBy = "profile")
   private List<AccountDetails> accountDetails;
}