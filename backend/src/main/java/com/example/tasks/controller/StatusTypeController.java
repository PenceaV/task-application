package com.example.tasks.controller;

import com.example.tasks.dto.StatusTypeDTO;
import com.example.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    @GetMapping
    public List<StatusTypeDTO> getAllStatuses() {
        return statusTypeService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public StatusTypeDTO getStatusById(@PathVariable @NotBlank String id) {
        return statusTypeService.getStatusById(id);
    }

    @PostMapping
    public StatusTypeDTO createStatus(@RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.createStatus(statusTypeDTO);
    }

    @PutMapping("/{id}")
    public StatusTypeDTO updateStatus(@PathVariable @NotBlank String id, @RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.updateStatus(id, statusTypeDTO);
    }

    @DeleteMapping("/{id}")
    public StatusTypeDTO deleteStatus(@PathVariable @NotBlank String id) {
        return statusTypeService.deleteStatus(id);
    }
}
