package com.example.tasks.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusType {

    @Id
    @Column(name = "STATUS_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statusTypeId;

    @OneToMany(mappedBy = "statusType")
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "STATUS_NAME", nullable = false)
    private String statusName;

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