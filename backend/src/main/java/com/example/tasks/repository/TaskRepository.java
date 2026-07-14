package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t 
        FROM Task t 
        WHERE t.statusType.statusName IN ('In Progress', 'Not Started')
        ORDER BY t.dueDate ASC
    """)
    List<Task> findByActiveStatus();

    List<Task> findByUser_UserId(Long userId);

    List<Task> findByUser_Username(String username);

    List<Task> findByStatusType_StatusName(String statusName);
}
