package com.bank.publicinfo.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 40)
    private String entityType;

    @Column(nullable = false)
    private String operationType;

    @Column(nullable = false)
    private String createdBy;

    @Column
    private String modifiedBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @Column
    @Lob
    private String newEntityJson;

    @Column(nullable = false)
    @Lob
    private String entityJson;








}
