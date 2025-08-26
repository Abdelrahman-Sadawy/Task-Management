package com.self.task_management.dto.request;

import com.self.task_management.model.enums.TaskStatus;
import com.self.task_management.util.ErrorConstants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskCreationReqDTO {
    @NotNull(message = ErrorConstants.USER_ID_ERROR_CODE)
    private Long userId;
    @NotNull(message = ErrorConstants.TASK_NAME_ERROR_CODE)
    private String taskTitle;
    private String taskDescription;
    private TaskStatus taskStatus;
    private LocalDate dueDate;

}
