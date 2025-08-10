package com.self.task_management.controller;

import com.self.task_management.dto.request.UserLoginReqDTO;
import com.self.task_management.dto.request.UserRegistrationReqDTO;
import com.self.task_management.dto.response.AuthResDTO;
import com.self.task_management.dto.response.UserResDTO;
import com.self.task_management.service.UserService;
import com.self.task_management.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
     private final String CONTROLLER_NAME = this.getClass().getName();
     private final UserService userService;

     @PostMapping("/register")
     public ResponseEntity<UserResDTO> register(@Valid @RequestBody UserRegistrationReqDTO request, BindingResult bindingResult) {
         ValidationUtils.checkValidationErrors(bindingResult);
         log.info("{}:: register STARTED with request: {}" , CONTROLLER_NAME, request);
         UserResDTO response = userService.register(request);
         log.info("{}:: register FINISHED with response: {}" , CONTROLLER_NAME, response);
         return ResponseEntity.ok(response);
     }

    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> login(@Valid @RequestBody UserLoginReqDTO request, BindingResult bindingResult){
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{}:: login STARTED with request: {}" , CONTROLLER_NAME, request);
        AuthResDTO response = userService.login(request);
        log.info("{}:: login FINISHED with response: {}" , CONTROLLER_NAME, response);
        return ResponseEntity.ok(response);
    }
}
