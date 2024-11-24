package com.bank.publicinfo.entity;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "bank_details")
@Getter
@Setter
@ToString
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
    private Integer corAccount;

    @Column(nullable = false, length = 180)
    private String city;

    @Column(nullable = false, length = 15)
    private String jointStockCompany;

    @Column(nullable = false, length = 80)
    private String name;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<License> licenses;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Certificate> certificates;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BankDetails that = (BankDetails) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
