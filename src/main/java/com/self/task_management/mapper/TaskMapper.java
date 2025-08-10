package com.self.task_management.mapper;

import com.self.task_management.dto.request.TaskCreationReqDTO;
import com.self.task_management.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskCreationReqDTO reqDTO);
}
