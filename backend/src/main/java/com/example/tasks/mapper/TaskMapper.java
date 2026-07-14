package com.example.tasks.mapper;

import com.example.tasks.domain.Task;
import com.example.tasks.dto.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .userId(task.getUser().getUserId())
                .statusTypeId(task.getStatusType().getStatusTypeId())
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy())
                .creationDate(task.getCreationDate())
                .lastUpdateDate(task.getLastUpdateDate())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .createdByFullName(task.getCreatedByFullName())
                .build();
    }

    public Task toEntity(TaskDTO taskDTO) {
        return Task.builder()
                .taskName(taskDTO.getTaskName())
                .dueDate(taskDTO.getDueDate())
                .createdBy(taskDTO.getCreatedBy())
                .createdByFullName(taskDTO.getCreatedByFullName())
                .build();
    }
}
