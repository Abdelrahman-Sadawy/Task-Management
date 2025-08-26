package com.self.task_management.service;

import com.self.task_management.dto.request.*;
import com.self.task_management.dto.response.AuthResDTO;
import com.self.task_management.dto.response.UserResDTO;
import com.self.task_management.exception.CustomException;
import com.self.task_management.mapper.UserMapper;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final String SERVICE = this.getClass().getSimpleName();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Transactional
    public UserResDTO register(UserRegistrationReqDTO request) {
        log.info("{}:: register method: STARTED with email:  {}", SERVICE, request.getEmail());

        String email = request.getEmail();
        validateUniqueEmail(email);

        User user = createUser(request);
        User savedUser = userRepository.save(user);

        log.info("{}:: register method: userId: {}  userEmail: {}", SERVICE, savedUser.getUserId(), savedUser.getEmail());

        return userMapper.toResponseDto(savedUser);
    }

    public AuthResDTO login(UserLoginReqDTO request) {
        log.info("{}:: login method: STARTED with request:  {}", SERVICE, request);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new CustomException(ErrorCodeEnum.WRONG_EMAIL_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        User user = getUserByEmail(request.getEmail());
        log.info("{}:: login method: user:  {}", SERVICE, user);

        String token = jwtTokenProvider.generateToken(user.getEmail());
        log.info("{}:: login method: token:  {}", SERVICE, token);

        UserResDTO userResDTO = UserResDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .userId(user.getUserId())
                .createdAt(user.getCreatedAt())
                .build();
        log.info("{}:: login method: userResDTO:  {}", SERVICE, userResDTO);

        return AuthResDTO.builder()
                .user(userResDTO)
                .token(token)
                .build();
    }


    public User getUserByEmail(String email) {
        log.info("{}:: getUserByEmail method: STARTED with email:  {}", SERVICE, email);

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND_ERROR, HttpStatus.BAD_REQUEST)
        );
        log.info("{}:: getUserByEmail method: COMPLETED for email:  {}", SERVICE, email);
        return user;
    }

    public User getUserById(Long userId) {
        log.info("{}:: getUserById method: STARTED with userId:  {}", SERVICE, userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND_ERROR, HttpStatus.BAD_REQUEST)
        );
        log.info("{}:: getUserById method: COMPLETED for userId:  {}", SERVICE, userId);
        return user;
    }

    private void validateUniqueEmail(String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            throw new CustomException(ErrorCodeEnum.EMAIL_FOUND_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    private User createUser(UserRegistrationReqDTO request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
    
    @Transactional
    public UserResDTO update(UpdateUserReqDto request) {
        log.info("{}:: update method: STARTED with request:  {}", SERVICE, request);

        if (request.getUserId() == null) {
            throw new CustomException(ErrorCodeEnum.USER_ID_ERROR, HttpStatus.BAD_REQUEST);
        }
        
        User user = getUserById(request.getUserId());
        
        String newUsername = request.getUsername();
        String newEmail = request.getEmail();
        applyUpdates(user, newUsername, newEmail);
        
        User updatedUser = userRepository.save(user);

        log.info("{}:: update method: COMPLETED for userId:  {}", SERVICE, updatedUser.getUserId());
        return userMapper.toResponseDto(updatedUser);
    }

    private void applyUpdates(User user, String newUsername, String newEmail) {
        if (newUsername != null && !newUsername.isBlank() && !newUsername.equals(user.getUsername())) {
            user.setUsername(newUsername);
        }
        if (newEmail != null && !newEmail.isBlank() && !newEmail.equals(user.getEmail())) {
            validateUniqueEmail(newEmail);
            user.setEmail(newEmail);
        }
    }

    public UserResDTO resetPassword(ResetPasswordReqDto request) {
        log.info("{}:: resetPassword method: STARTED with email: {}", SERVICE, request.getEmail());

        User user = getUserByEmail(request.getEmail());
        String resetToken = jwtTokenProvider.generateToken(user.getEmail());

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
        } catch (Exception e) {
            log.error("{}:: resetPassword method: Failed to send email: {}", SERVICE, e.getMessage());
            throw new CustomException(ErrorCodeEnum.EMAIL_SENDING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("{}:: resetPassword method: COMPLETED for email: {}", SERVICE, request.getEmail());
        return userMapper.toResponseDto(user);
    }

    public UserResDTO updatePassword(UpdatePasswordReqDto request) {
        log.info("{}:: updatePassword method: STARTED with request: {}", SERVICE, request);
        Long userId = request.getUserId();
        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();

        User user = getUserById(userId);

        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            log.info("{}:: updatePassword method: COMPLETED for userId: {}", SERVICE, userId);
            return userMapper.toResponseDto(user);
        }

        log.info("{}:: updatePassword method: FAILED for userId: {}", SERVICE, userId);
        return null;
    }
}
