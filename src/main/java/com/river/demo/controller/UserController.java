package com.river.demo.controller;

import com.river.demo.dto.ApiResponse;
import com.river.demo.dto.UserListResponse;
import com.river.demo.dto.UserRegistrationRequest;
import com.river.demo.dto.UserSearchRequest;
import com.river.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @GetMapping("/search")
    public ResponseEntity<UserListResponse> searchUser(@Valid @RequestBody UserSearchRequest request) {
        return ResponseEntity.ok(userService.searchUser(request));
    }
}
