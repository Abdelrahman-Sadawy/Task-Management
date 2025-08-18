package com.self.task_management.service;

import com.self.task_management.dto.request.TaskCreationReqDTO;
import com.self.task_management.dto.response.TaskResDTO;
import com.self.task_management.exception.CustomException;
import com.self.task_management.mapper.TaskMapper;
import com.self.task_management.model.Task;
import com.self.task_management.model.User;
import com.self.task_management.model.enums.TaskStatus;
import com.self.task_management.repository.TaskRepository;
import com.self.task_management.util.ErrorCodeEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final String SERVICE = this.getClass().getSimpleName();
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResDTO createTask(@Valid TaskCreationReqDTO request, String email) {
        log.info("{}:: createTask starting with request: {} ", SERVICE, request);

        User user = userService.getUserByEmail(email);
        validateUserOwnership(user, request.getUserId());

        User userCheck = userService.getUserById(request.getUserId());
        log.info("{}:: createTask userCheck: {} email: {}", SERVICE, userCheck.getEmail(), email);

        Task task = buildTaskFrom(request, user);

        Task savedTask = taskRepository.save(task);

        TaskResDTO response = taskMapper.toResponseDto(savedTask);

        log.info("{}:: createTask response: {} ", SERVICE, response);
        return response;
    }

    private void validateUserOwnership(User user, Long requestedUserId) {
        if (!Objects.equals(user.getUserId(), requestedUserId)) {
            throw new CustomException(ErrorCodeEnum.USER_ID_NOT_RELATED_TO_EMAIL, HttpStatus.BAD_REQUEST);
        }
    }
    private Task buildTaskFrom(TaskCreationReqDTO request, User user) {
        TaskStatus status = Optional.ofNullable(request.getTaskStatus()).orElse(TaskStatus.PENDING);
        return Task.builder()
                .title(request.getTaskTitle())
                .description(request.getTaskDescription())
                .status(status)
                .dueDate(request.getDueDate())
                .user(user)
                .build();
    }


}
