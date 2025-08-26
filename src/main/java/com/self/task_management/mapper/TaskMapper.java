package com.self.task_management.mapper;

import com.self.task_management.dto.request.TaskCreationReqDTO;
import com.self.task_management.dto.response.TaskResDTO;
import com.self.task_management.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskCreationReqDTO reqDTO);

    @Mapping(source = "title" , target = "taskTitle")
    @Mapping(source = "description" , target = "taskDescription")
    @Mapping(source = "status" , target = "taskStatus")
    @Mapping(source = "dueDate" , target = "dueDate")
    @Mapping(source = "user.userId" , target = "userId")
    @Mapping(source = "user.username" , target = "username")
    TaskResDTO toResponseDto(Task task);
}
