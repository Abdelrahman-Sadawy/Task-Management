package com.self.task_management.util;

import com.self.task_management.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class ValidationUtils {
    public static void checkValidationErrors(BindingResult result) {
        if (result.hasErrors()) {
            String codes = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(","));

            String messages = result.getFieldErrors().stream()
                    .map(error -> Arrays.stream(ErrorCodeEnum.values())
                            .filter(e -> e.getCode().equals(error.getDefaultMessage()))
                            .findFirst()
                            .map(ErrorCodeEnum::getDescription)
                            .orElse(ErrorCodeEnum.DEFAULT.getDescription()))
                    .collect(Collectors.joining(", "));

            log.error("request with field errors: {}", codes);

            throw new CustomException(HttpStatus.BAD_REQUEST, codes, messages);
        }
    }

    public static void checkValidationConstraintErrors(BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result);
        }
    }

}
