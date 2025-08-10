package com.self.task_management.service;

import com.self.task_management.dto.request.UserLoginReqDTO;
import com.self.task_management.dto.request.UserRegistrationReqDTO;
import com.self.task_management.dto.response.AuthResDTO;
import com.self.task_management.dto.response.UserResDTO;
import com.self.task_management.exception.CustomException;
import com.self.task_management.model.User;
import com.self.task_management.repository.UserRepository;
import com.self.task_management.security.JwtTokenProvider;
import com.self.task_management.util.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final String SERVICE_NAME = this.getClass().getName();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public UserResDTO register(UserRegistrationReqDTO request) {
        log.info("{}:: register method: STARTED with request:  {}", SERVICE_NAME, request);

        String email = request.getEmail();
        boolean isEmailExists = userRepository.existsByEmail(email);

        if (isEmailExists) throw new CustomException(ErrorCodeEnum.EMAIL_FOUND_ERROR, HttpStatus.BAD_REQUEST);

        User user = User.builder()
                .username(request.getUsername())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        log.info("{}:: register method: user after save {}", SERVICE_NAME, savedUser);

        return UserResDTO.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    public AuthResDTO login(UserLoginReqDTO request) {
        log.info("{}:: login method: STARTED with request:  {}", SERVICE_NAME, request);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCodeEnum.WRONG_EMAIL_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND_ERROR, HttpStatus.BAD_REQUEST)
        );

        String token = jwtTokenProvider.generateToken(user.getEmail());
        log.info("{}:: login method: token:  {}", SERVICE_NAME, token);

        UserResDTO userResDTO = UserResDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .userId(user.getUserId())
                .createdAt(user.getCreatedAt())
                .build();
        log.info("{}:: login method: userResDTO:  {}", SERVICE_NAME, userResDTO);

        return AuthResDTO.builder()
                .user(userResDTO)
                .token(token)
                .build();
    }
}
