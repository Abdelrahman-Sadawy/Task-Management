package com.self.task_management.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.self.task_management.util.ErrorConstants.*;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    USER_NAME_REQUIRED_ERROR("Username is required", USER_NAME_NULL_CODE, "Username is required"),
    EMAIL_REQUIRED_ERROR("Email is required", EMAIL_NULL_CODE, "Email is required"),
    PASSWORD_REQUIRED_ERROR("Password is required", PASSWORD_NULL_CODE, "Password is required"),
    USER_NAME_FORMAT_ERROR("Please enter a right format for username", USER_NAME_FORMAT_CODE, "Please enter a right format for username"),
    EMAIL_FORMAT_ERROR("Please enter a right format for email", EMAIL_FORMAT_CODE, "Please enter a right format for email"),
    PASSWORD_FORMAT_ERROR("Please enter a right format for password", PASSWORD_FORMAT_CODE, "Please enter a right format for password"),
    USER_NOT_FOUND_ERROR("User not found", USER_NOT_FOUND_CODE, "User not found"),
    EMAIL_FOUND_ERROR("Email found, please create new one", EMAIL_FOUND_CODE, "Email found, please create new one"),
    WRONG_EMAIL_PASSWORD("Email or Password one of them wrong, please try again", AUTHENTICATION_ERROR_CODE, "Email or Password one of them wrong, please try again"),
    DEFAULT("Default error", DEFAULT_CODE, "Default error");
    private final String key;
    private final String code;
    private final String description;
}
