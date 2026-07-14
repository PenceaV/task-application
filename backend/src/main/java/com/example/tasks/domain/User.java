package com.example.tasks.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "BIRTH_DATE")
    private LocalDateTime birthDate;

    @Column(name = "IS_INTERNAL")
    @Builder.Default
    private Integer isInternal = 1; // ar trb sa fie Boolean

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "CREATION_DATE", nullable = false)
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    @Builder.Default
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    private String lastUpdatedBy;

    @Column(name = "CREATED_BY_FULLNAME")
    private String createdByFullName;
}