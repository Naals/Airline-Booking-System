package com.project.userservice.service;

import com.project.commonlib.exception.ResourceNotFoundException; // Replace with your commonlib exception name if different
import com.project.commonlib.payload.request.UserRequest;
import com.project.commonlib.payload.response.UserResponse;
import com.project.userservice.mapper.UserMapper;
import com.project.userservice.modal.User;
import com.project.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("Fetching all registered users");
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        log.info("Fetching user details for ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.info("Fetching user details for email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateProfile(Long id, UserRequest request) {
        log.info("Updating profile details for user ID: {}", id);
        User user = getUserById(id);

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());


        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return UserMapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user record with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void verifyUser(Long id) {
        log.info("Toggling verification status to true for user ID: {}", id);
        User user = getUserById(id);
        user.setVerified(true);
        userRepository.save(user);
    }
}