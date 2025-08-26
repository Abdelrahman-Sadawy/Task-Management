package com.self.task_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordReqDto {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Current password cannot be empty")
    @ToString.Exclude
    private String currentPassword;

    @NotBlank(message = "New password cannot be empty")
    @ToString.Exclude
    private String newPassword;
}
