package com.bank.publicinfo.entity;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "branch")
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private Set<ATM> atms;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Branch branch = (Branch) o;
        return getId() != null && Objects.equals(getId(), branch.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
