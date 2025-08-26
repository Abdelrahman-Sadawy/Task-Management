package com.self.task_management.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.self.task_management.util.ErrorConstants.EMAIL_FORMAT_CODE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetPasswordReqDto {
    @Pattern(regexp = "^[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = EMAIL_FORMAT_CODE)
    private String email;

}
