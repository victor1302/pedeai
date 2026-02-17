package com.pedeai.dto.user;

import com.pedeai.entity.enums.UserType;

public record RegisterUserRequest(String username, String firstName, String lastName, String email, String password, String phone, UserType userType) {
}
