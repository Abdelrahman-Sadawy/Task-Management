package com.self.task_management.controller;

import com.self.task_management.dto.request.TaskCreationReqDTO;
import com.self.task_management.dto.response.TaskResDTO;
import com.self.task_management.service.TaskService;
import com.self.task_management.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final String CONTROLLER = this.getClass().getSimpleName();
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResDTO> createTask(@Valid @RequestBody TaskCreationReqDTO request,
                                                 BindingResult bindingResult){
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{} :: createTask starting with request: {} ", CONTROLLER, request);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        TaskResDTO response = taskService.createTask(request, email);
        log.info("{} :: createTask finished with response: {} ", CONTROLLER, response);
        return ResponseEntity.ok(response);
    }

}
