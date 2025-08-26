package com.self.task_management.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.self.task_management.util.ErrorConstants.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateUserReqDto {
    @NotNull
    private Long userId;

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$",message = USER_NAME_FORMAT_CODE)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = EMAIL_FORMAT_CODE)
    private String email;

}
