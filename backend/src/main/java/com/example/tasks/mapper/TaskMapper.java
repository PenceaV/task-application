package com.example.tasks.mapper;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.request.TaskCreateRequestDTO;
import com.example.tasks.dto.request.TaskUpdateRequestDTO;
import com.example.tasks.dto.response.TaskResponseDTO;
import com.example.tasks.dto.shared.StatusTypeSummaryDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public Task toEntity(TaskCreateRequestDTO taskDTO, User user, StatusType statusType) {
        return Task.builder()
                .taskName(taskDTO.getTaskName())
                .user(user)
                .statusType(statusType)
                .dueDate(taskDTO.getDueDate())
                .createdBy(taskDTO.getCreatedBy())
                .lastUpdatedBy(taskDTO.getCreatedBy())
                .build();
    }

    public void updateEntityFromDto(Task task, TaskUpdateRequestDTO taskDTO, User user, StatusType statusType) {
        task.setTaskName(taskDTO.getTaskName());
        task.setUser(user);
        task.setStatusType(statusType);
        task.setDueDate(taskDTO.getDueDate());
        task.setLastUpdatedBy(taskDTO.getLastUpdatedBy());
        task.setLastUpdateDate(LocalDateTime.now());
    }

    public TaskResponseDTO toResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .userId(task.getUser().getUserId())
                .statusType(
                        StatusTypeSummaryDTO.builder()
                                .statusTypeId(task.getStatusType().getStatusTypeId())
                                .statusName(task.getStatusType().getStatusName())
                                .build()
                )
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy())
                .creationDate(task.getCreationDate())
                .lastUpdateDate(task.getLastUpdateDate())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .createdByFullName(task.getCreatedByFullName())
                .build();
    }

}
