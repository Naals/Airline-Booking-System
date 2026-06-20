package com.project.commonlib.payload.response;

import com.project.commonlib.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private boolean verified;
    private LocalDateTime createdAt;
}
