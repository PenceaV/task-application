package com.example.tasks.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TaskFilterRequest {
    private String assignedTo;
    private String subject;
    private LocalDate dueDate;
    private String status;
    private String sortDir;
}
