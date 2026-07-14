package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.StatusTypeDTO;
import com.example.tasks.dto.TaskDTO;
import com.example.tasks.exception.StatusNotFoundException;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.exception.UserNotFoundException;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.repository.StatusTypeRepository;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final StatusTypeRepository statusTypeRepository;

    public List<TaskDTO> getAllTasks() {
        log.info("Tasks retrieved!");

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Retrieving task with id: {}", id);

        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public List<TaskDTO> getActiveTasks() {
        log.info("Retrieving active tasks!");

        return taskRepository.findByActiveStatus()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getOverdueTasks() {
        log.info("Retrieving overdue tasks!");

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now()))
                .toList();
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        log.info("Retrieving tasks for user id: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        return taskRepository.findByUser_UserId(userId)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTasksByUsername(String username) {
        log.info("Retrieving tasks for username: {}", username);

        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        return taskRepository.findByUser_Username(username)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTasksByStatusName(String statusName) {
        log.info("Retrieving tasks with status: {}", statusName);

        if (!statusTypeRepository.existsByStatusName(statusName)) {
            throw new StatusNotFoundException("Status not found with name: " + statusName);
        }

        return taskRepository.findByStatusType_StatusName(statusName)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Creating task: {}", taskDTO.getTaskName());

        Task task = taskMapper.toEntity(taskDTO);

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + taskDTO.getUserId()));
        StatusType statusType = statusTypeRepository.findById(taskDTO.getStatusTypeId())
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + taskDTO.getStatusTypeId()));

        task.setUser(user);
        task.setStatusType(statusType);
        task.setLastUpdatedBy(taskDTO.getCreatedBy());

        Task savedTask = taskRepository.save(task);

        return taskMapper.toDTO(savedTask);
    }
    

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Updating task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + taskDTO.getUserId()));
        StatusType statusType = statusTypeRepository.findById(taskDTO.getStatusTypeId())
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + taskDTO.getStatusTypeId()));

        task.setTaskName(taskDTO.getTaskName());
        task.setUser(user);
        task.setStatusType(statusType);
        task.setDueDate(taskDTO.getDueDate());
        task.setLastUpdatedBy(taskDTO.getCreatedBy());
        task.setLastUpdateDate(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toDTO(updatedTask);
    }
    
    @Transactional
    public TaskDTO updateTaskStatus(Long taskId, String statusName) {
        log.info("Updating status of task {} to {}", taskId, statusName);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        StatusType status = statusTypeRepository.findByStatusName(statusName)
                .orElseThrow(() -> new StatusNotFoundException("Status not found with name: " + statusName));

        task.setStatusType(status);
        task.setLastUpdatedBy(task.getCreatedBy());
        task.setLastUpdateDate(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toDTO(updatedTask);
    }
    
    @Transactional
    public TaskDTO deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        TaskDTO deletedTask = taskMapper.toDTO(task);

        taskRepository.delete(task);

        return deletedTask;
    }
}
