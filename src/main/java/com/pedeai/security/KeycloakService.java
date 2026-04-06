package com.pedeai.security;

import com.pedeai.customers.service.CustomerService;
import com.pedeai.customers.dto.CreateCostumerDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String createUser(CreateCostumerDto createCostumerDto) {
        UsersResource usersResource = keycloak.realm(realm).users();

        // Verifica se já existe pelo username ou email
        List<UserRepresentation> existingEmail = usersResource.searchByEmail(createCostumerDto.email(), true);
        List<UserRepresentation> existingUsername = usersResource.searchByUsername(createCostumerDto.username(), true);
        if (!existingEmail.isEmpty()) {
            throw new RuntimeException("Usuário com esse e-mail já existe.");
        }
        if (!existingUsername.isEmpty()) {
            throw new RuntimeException("Usuário com esse username já existe.");
        }

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(createCostumerDto.username());
        user.setEmail(createCostumerDto.email());
        user.setFirstName(createCostumerDto.firstName());
        user.setLastName(createCostumerDto.lastName());
        user.setEmailVerified(true);

        // Define a senha
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(createCostumerDto.password());
        user.setCredentials(List.of(credential));

        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == 201) {
                return CreatedResponseUtil.getCreatedId(response);
            } else {
                throw new RuntimeException("Erro ao criar usuário: " + response.getStatus());
            }
        }

    }

}
