package com.pedeai.service;

import com.pedeai.dto.user.RegisterUserRequest;
import com.pedeai.dto.user.RegisterUserResponse;
import com.pedeai.entity.User;
import com.pedeai.entity.enums.UserType;
import com.pedeai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakAdminService keycloakAdminService;

    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest){
        RegisterUserResponse keycloackResponse = keycloakAdminService.createUser(registerUserRequest);
        User user = new User();
        user.setKeycloakId(keycloackResponse.keycloakId());
        user.setEmail(registerUserRequest.email());
        user.setName(registerUserRequest.firstName());
        user.setUserType(registerUserRequest.userType());

        userRepository.save(user);

        return new RegisterUserResponse(
                user.getKeycloakId(),
                user.getName(),
                user.getEmail()
        );
    }
}
