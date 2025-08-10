package com.self.task_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResDTO {
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
