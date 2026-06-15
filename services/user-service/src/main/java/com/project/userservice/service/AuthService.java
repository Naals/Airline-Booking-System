package com.project.userservice.service;

import com.project.commonlib.enums.UserRole;
import com.project.commonlib.exception.DuplicateResourceException;
import com.project.commonlib.payload.request.AuthRequest;
import com.project.commonlib.payload.request.UserRequest;
import com.project.commonlib.payload.response.AuthResponse;
import com.project.userservice.modal.User;
import com.project.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(UserRequest request) {
        log.info("Processing registration request for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email address is already registered.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole() != null ? request.getRole() : UserRole.PASSENGER)
                .verified(false)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }


    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        log.info("Processing authentication validation for email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User records could not be verified."));

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}