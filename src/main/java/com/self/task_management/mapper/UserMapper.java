package com.self.task_management.mapper;

import com.self.task_management.dto.request.UserRegistrationReqDTO;
import com.self.task_management.dto.response.UserResDTO;
import com.self.task_management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRegistrationReqDTO reqDTO);

    UserResDTO toResponseDto(User user);
}
