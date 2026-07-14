package com.example.tasks.service;

import com.example.tasks.exception.DuplicateStatusException;
import com.example.tasks.exception.StatusNotFoundException;
import com.example.tasks.domain.StatusType;
import com.example.tasks.dto.StatusTypeDTO;
import com.example.tasks.mapper.StatusTypeMapper;
import com.example.tasks.repository.StatusTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeService {
    private static final String STATUS_NOT_FOUND_MESSAGE = "Status not found with id: ";
    private final StatusTypeRepository statusTypeRepository;
    private final StatusTypeMapper statusMapper;

    public List<StatusTypeDTO> getAllStatuses() {
        log.info("Statuses retrieved!");
        return statusTypeRepository.findAll()
                .stream()
                .map(statusMapper::toDto)
                .toList();
    }

    public StatusTypeDTO getStatusById(String id) {
        log.info("Retrieving status with id: {}!", id);

        return statusTypeRepository.findById(id)
                .map(statusMapper::toDto)
                .orElseThrow(() -> new StatusNotFoundException(STATUS_NOT_FOUND_MESSAGE + id));
    }

    @Transactional
    public StatusTypeDTO createStatus(StatusTypeDTO statusTypeDTO) {
        log.info("Creating status: {}", statusTypeDTO.getStatusName());

        StatusType status = statusMapper.toEntity(statusTypeDTO);

        if (statusTypeRepository.existsByStatusName(status.getStatusName()))
            throw new DuplicateStatusException("Status type already exists!");

        status.setLastUpdatedBy(status.getCreatedBy());
        StatusType savedStatus = statusTypeRepository.save(status);

        return statusMapper.toDto(savedStatus);
    }

    @Transactional
    public StatusTypeDTO updateStatus(String id, StatusTypeDTO statusTypeDTO) {
        log.info("Updating status with id: {}", id);

        StatusType status = statusTypeRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException(STATUS_NOT_FOUND_MESSAGE + id));

        status.setStatusName(statusTypeDTO.getStatusName());
        status.setLastUpdatedBy(statusTypeDTO.getCreatedBy());
        status.setLastUpdateDate(LocalDateTime.now());

        StatusType updatedStatus = statusTypeRepository.save(status);

        return statusMapper.toDto(updatedStatus);
    }

    @Transactional
    public StatusTypeDTO deleteStatus(String id) {
        log.info("Deleting status with id: {}", id);

        StatusType status = statusTypeRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException(STATUS_NOT_FOUND_MESSAGE + id));

        StatusTypeDTO deletedStatus = statusMapper.toDto(status);

        statusTypeRepository.delete(status);

        return deletedStatus;
    }
}