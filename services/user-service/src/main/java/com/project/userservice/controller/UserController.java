package com.project.userservice.controller;

import com.project.commonlib.payload.request.UserRequest;
import com.project.commonlib.payload.response.ApiResponse;
import com.project.commonlib.payload.response.UserResponse;
import com.project.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        userService.getAllUsers(),
                        "Users retrieved successfully"
                )
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        userService.getUserByEmail(email),
                        "User retrieved successfully"
                )
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        userService.updateProfile(id, request),
                        "Profile updated successfully"
                )
        );
    }
    @PatchMapping("/{id}/verify")
    public ResponseEntity<ApiResponse<Void>> verifyUser(
            @PathVariable Long id
    ) {
        userService.verifyUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        null,
                        "User verified successfully"
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        null,
                        "User deleted successfully"
                )
        );
    }

}