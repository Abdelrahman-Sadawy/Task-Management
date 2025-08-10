package com.self.task_management.exception;

import com.self.task_management.util.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String code;
    private String message;

    public CustomException(String message){
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public CustomException(ErrorCodeEnum errorCodeEnum, HttpStatus httpStatus){
        this.message = errorCodeEnum.getDescription();
        this.code = errorCodeEnum.getCode();
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, ErrorCodeEnum errorCodeEnum){
        super(message);
        this.message = errorCodeEnum.getDescription();
        this.code = errorCodeEnum.getCode();

    }

    public CustomException(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
