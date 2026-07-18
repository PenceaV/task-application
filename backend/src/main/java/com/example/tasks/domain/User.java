package com.example.tasks.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "IS_INTERNAL")
    @Builder.Default
    private Integer isInternal = 0; // ar trb sa fie Boolean

    @Column(name = "CREATED_BY", nullable = false)
    @Builder.Default
    private String createdBy = "SYSTEM";

    @Column(name = "CREATION_DATE", nullable = false)
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    @Builder.Default
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    @Builder.Default
    private String lastUpdatedBy = "SYSTEM";

    @Column(name = "CREATED_BY_FULLNAME")
    @Builder.Default
    private String createdByFullName = "System";
}