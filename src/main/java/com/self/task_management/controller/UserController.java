package com.self.task_management.controller;

import com.self.task_management.dto.request.*;
import com.self.task_management.dto.response.AuthResDTO;
import com.self.task_management.dto.response.UserResDTO;
import com.self.task_management.service.UserService;
import com.self.task_management.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
     private final String CONTROLLER = this.getClass().getSimpleName();
     private final UserService userService;

     /**
      * Handles user registration requests. Validates the incoming request, processes it,
      * and returns a response with the registered user details.
      *
      * @param request the request body containing user registration details
      * @param bindingResult object holding the validation results of the request
      * @return a ResponseEntity containing the registered user details
      *         encapsulated in a UserResDTO object
      */
     @PostMapping("/register")
     public ResponseEntity<UserResDTO> register(@Valid @RequestBody UserRegistrationReqDTO request, BindingResult bindingResult) {
         ValidationUtils.checkValidationErrors(bindingResult);
         log.info("{}:: register STARTED with request: {}" , CONTROLLER, request);
         UserResDTO response = userService.register(request);
         log.info("{}:: register FINISHED with response: {}" , CONTROLLER, response);
         return ResponseEntity.ok(response);
     }

    /**
     * Handles the login process for a user by validating the provided credentials
     * and returning an authentication response containing a token and user details.
     *
     * @param request the login request data including email and password
     * @param bindingResult the result of validation checks performed on the request
     * @return a ResponseEntity containing the authentication response details
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResDTO> login(@Valid @RequestBody UserLoginReqDTO request, BindingResult bindingResult){
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{}:: login STARTED with request: {}" , CONTROLLER, request);
        AuthResDTO response = userService.login(request);
        log.info("{}:: login FINISHED with response: {}" , CONTROLLER, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates user information based on the provided request data.
     *
     * @param request       the request containing updated user information
     * @param bindingResult object holding the validation results of the request
     * @return a ResponseEntity containing the updated user details
     */
    @PutMapping("/update")
    public ResponseEntity<UserResDTO> update(@Valid @RequestBody UpdateUserReqDto request, BindingResult bindingResult){
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{}:: update STARTED with request: {}", CONTROLLER, request);
        UserResDTO response = userService.update(request);
        log.info("{}:: update FINISHED with response: {}", CONTROLLER, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Initiates the password reset process for a user with the specified email.
     *
     * @param request         the email address of the user requesting password reset
     * @param bindingResult object holding the validation results of the request
     * @return a ResponseEntity containing the user details after initiating reset
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordReqDto request, BindingResult bindingResult) {
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{}:: resetPassword STARTED with email: {}", CONTROLLER, request);
        UserResDTO response = userService.resetPassword(request);
        log.info("{}:: resetPassword FINISHED with response: {}", CONTROLLER, response);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the password for an authenticated user.
     *
     * @param request       the request containing the new password information
     * @param bindingResult object holding the validation results of the request
     * @return a ResponseEntity containing the updated user details
     */
    @PutMapping("/update-password")
    public ResponseEntity<UserResDTO> updatePassword(@Valid @RequestBody UpdatePasswordReqDto request, BindingResult bindingResult) {
        ValidationUtils.checkValidationErrors(bindingResult);
        log.info("{}:: updatePassword STARTED with request: {}", CONTROLLER, request);
        UserResDTO response = userService.updatePassword(request);
        log.info("{}:: updatePassword FINISHED with response: {}", CONTROLLER, response);
        return ResponseEntity.ok(response);
    }

}
