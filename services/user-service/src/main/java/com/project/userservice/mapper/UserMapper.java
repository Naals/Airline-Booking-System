package com.project.userservice.mapper;

import com.project.commonlib.payload.response.UserResponse;
import com.project.userservice.modal.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private UserMapper() {
    }


    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .verified(user.isVerified())
                .createdAt(user.getCreatedAt())
                .build();
    }
}