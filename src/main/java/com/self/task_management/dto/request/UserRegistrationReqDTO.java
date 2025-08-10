package com.self.task_management.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.self.task_management.util.ErrorConstants.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRegistrationReqDTO {
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$",message = USER_NAME_FORMAT_CODE)
    @NotNull(message = USER_NAME_NULL_CODE)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]+(?:\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,7}$", message = EMAIL_FORMAT_CODE)
    @NotNull(message = EMAIL_NULL_CODE)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@$#&_\\-.]).{8,}$",message = PASSWORD_FORMAT_CODE)
    @NotNull(message = PASSWORD_NULL_CODE)
    @ToString.Exclude
    private String password;
}
