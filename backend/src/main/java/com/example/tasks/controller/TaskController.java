package com.example.tasks.controller;

import com.example.tasks.dto.request.TaskCreateRequestDTO;
import com.example.tasks.dto.request.TaskUpdateRequestDTO;
import com.example.tasks.dto.response.TaskResponseDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/id/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/active")
    public List<TaskResponseDTO> getActiveTasks() {
        return taskService.getActiveTasks();
    }

    @GetMapping("/overdue")
    public List<TaskResponseDTO> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponseDTO> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping("/username/{username}")
    public List<TaskResponseDTO> getTasksByUsername(@PathVariable String username) {
        return taskService.getTasksByUsername(username);
    }

    @GetMapping("/status")
    public List<TaskResponseDTO> getTasksByStatusName(@RequestParam String statusName) {
        return taskService.getTasksByStatusName(statusName);
    }

    @PostMapping
    public TaskResponseDTO createTask(@RequestBody @Valid TaskCreateRequestDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequestDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @PatchMapping("/{id}/status")
    public TaskResponseDTO updateTaskStatus(@PathVariable Long id, @RequestParam String statusName) {
        return taskService.updateTaskStatus(id, statusName);
    }

    @DeleteMapping("/{id}")
    public TaskResponseDTO deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
