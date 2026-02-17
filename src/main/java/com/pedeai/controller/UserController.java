package com.pedeai.controller;

import com.pedeai.dto.user.RegisterUserRequest;
import com.pedeai.dto.user.RegisterUserResponse;
import com.pedeai.entity.User;
import com.pedeai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        return ResponseEntity.ok(userService.registerUser(registerUserRequest));
    }
}
