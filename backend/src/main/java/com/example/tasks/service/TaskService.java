package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.request.TaskCreateRequestDTO;
import com.example.tasks.dto.request.TaskFilterRequest;
import com.example.tasks.dto.request.TaskUpdateRequestDTO;
import com.example.tasks.dto.response.TaskResponseDTO;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final StatusTypeRepository statusTypeRepository;

    public List<TaskResponseDTO> getAllTasks() {
        log.info("Tasks retrieved!");

        return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "dueDate"))
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id) {
        log.info("Retrieving task with id: {}", id);

        return taskRepository.findById(id)
                .map(taskMapper::toResponseDTO)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public List<TaskResponseDTO> getActiveTasks() {
        log.info("Retrieving active tasks!");

        return taskRepository.findByActiveStatus()
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByFilter(TaskFilterRequest filterRequest) {
        LocalDateTime dueDateStart = filterRequest.getDueDate() != null ? filterRequest.getDueDate().atStartOfDay() : null;
        LocalDateTime dueDateEnd = filterRequest.getDueDate() != null ? filterRequest.getDueDate().plusDays(1).atStartOfDay() : null;

        Sort sort = "asc".equalsIgnoreCase(filterRequest.getSortDir())
                ? Sort.by(Sort.Direction.ASC, "dueDate")
                : Sort.by(Sort.Direction.DESC, "dueDate");

        return taskRepository.findByFilter(
                filterRequest.getAssignedTo(),
                filterRequest.getSubject(),
                dueDateStart,
                dueDateEnd,
                filterRequest.getStatus(),
                sort
        ).stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getOverdueTasks() {
        log.info("Retrieving overdue tasks!");

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponseDTO)
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now()))
                .toList();
    }

    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        log.info("Retrieving tasks for user id: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        return taskRepository.findByUser_UserId(userId)
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByUsername(String username) {
        log.info("Retrieving tasks for username: {}", username);

        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User not found with username: " + username);
        }

        return taskRepository.findByUser_Username(username)
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByStatusName(String statusName) {
        log.info("Retrieving tasks with status: {}", statusName);

        if (!statusTypeRepository.existsByStatusName(statusName)) {
            throw new StatusNotFoundException("Status not found with name: " + statusName);
        }

        return taskRepository.findByStatusType_StatusName(statusName)
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public TaskResponseDTO createTask(TaskCreateRequestDTO taskDTO) {
        log.info("Creating task: {}", taskDTO.getTaskName());

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + taskDTO.getUserId()));
        StatusType statusType = statusTypeRepository.findById(taskDTO.getStatusTypeId())
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + taskDTO.getStatusTypeId()));

        Task task = taskMapper.toEntity(taskDTO, user, statusType);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponseDTO(savedTask);
    }
    

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskUpdateRequestDTO taskDTO) {
        log.info("Updating task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + taskDTO.getUserId()));
        StatusType statusType = statusTypeRepository.findById(taskDTO.getStatusTypeId())
                .orElseThrow(() -> new StatusNotFoundException("Status not found with id: " + taskDTO.getStatusTypeId()));

        taskMapper.updateEntityFromDto(task, taskDTO, user, statusType);

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toResponseDTO(updatedTask);
    }
    
    @Transactional
    public TaskResponseDTO updateTaskStatus(Long taskId, String statusName) {
        log.info("Updating status of task {} to {}", taskId, statusName);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        StatusType status = statusTypeRepository.findByStatusName(statusName)
                .orElseThrow(() -> new StatusNotFoundException("Status not found with name: " + statusName));

        task.setStatusType(status);
        task.setLastUpdatedBy(task.getCreatedBy()); // TODO: to be changed when i have auth
        task.setLastUpdateDate(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toResponseDTO(updatedTask);
    }
    
    @Transactional
    public TaskResponseDTO deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        TaskResponseDTO deletedTask = taskMapper.toResponseDTO(task);

        taskRepository.delete(task);

        return deletedTask;
    }
}
