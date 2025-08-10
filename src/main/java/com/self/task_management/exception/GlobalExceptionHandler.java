package com.self.task_management.exception;

import com.self.task_management.dto.util.ErrorDTO;
import com.self.task_management.util.ErrorCodeEnum;
import jakarta.validation.ConstraintDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> handle(CustomException ex) {
        log.error(">>> handle custom exception Triggered <<<");
        return ResponseEntity.status(ex.getHttpStatus())
                .body(
                        ErrorDTO.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(ConstraintDefinitionException.class)
    public ResponseEntity<ErrorDTO> handleConstraints(ConstraintDefinitionException ex) {
        log.error(">>> handleConstraints Triggered <<<");
        log.error("{} - reason: {}", ex.getLocalizedMessage(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(
                        ErrorDTO.builder()
                                .code(extractErrorCodeFromMessage(ex.toString()))
                                .message(ex.getMessage())
                                .build()
                );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(">>> Validation Handler Triggered <<<");
        List<String> codes = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String codeKey = error.getDefaultMessage();
            ErrorCodeEnum enumVal = Arrays.stream(ErrorCodeEnum.values())
                    .filter(e -> e.getCode().equals(codeKey))
                    .findFirst()
                    .orElse(null);

            if (enumVal != null) {
                codes.add(enumVal.getCode());
                messages.add(enumVal.getDescription());
            } else {
                codes.add(ErrorCodeEnum.DEFAULT.getCode());
                messages.add(ErrorCodeEnum.DEFAULT.getDescription());
            }
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.builder()
                        .code(String.join(",", codes))
                        .message(String.join(", ", messages))
                        .build());

    }

    private String extractErrorCodeFromMessage(String message) {
        if (isBlank(message)) return Strings.EMPTY;
        String[] split = message
                .replace("jakarta.validation.ConstraintDefinitionException", "")
                .split(":")[1]
                .split(",");

        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            builder.append(s.split("-")[0].trim());
            builder.append(",");
        }

        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

}
