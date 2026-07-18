package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t 
        FROM Task t 
        WHERE t.statusType.statusName IN ('In Progress', 'Not Started')
        ORDER BY t.dueDate ASC
    """)
    List<Task> findByActiveStatus();

    @Query("""
        SELECT t FROM Task t
        WHERE (:assignedTo IS NULL OR t.user.username = :assignedTo)
          AND (:subject IS NULL OR LOWER(t.taskName) LIKE LOWER(CONCAT('%', :subject, '%')))
          AND (:dueDateStart IS NULL OR t.dueDate >= :dueDateStart)
          AND (:dueDateEnd IS NULL OR t.dueDate < :dueDateEnd)
          AND (:status IS NULL OR t.statusType.statusName = :status)
        """)
    List<Task> findByFilter(
            @Param("assignedTo") String assignedTo,
            @Param("subject") String subject,
            @Param("dueDateStart") LocalDateTime dueDateStart,
            @Param("dueDateEnd") LocalDateTime dueDateEnd,
            @Param("status") String status,
            Sort sort);

    List<Task> findByUser_UserId(Long userId);

    List<Task> findByUser_Username(String username);

    List<Task> findByStatusType_StatusName(String statusName);
}
