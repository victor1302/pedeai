package com.pedeai.service;

import com.pedeai.dto.user.RegisterUserRequest;
import com.pedeai.dto.user.RegisterUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    @Value("${keycloak.server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.admin.username}")
    private String adminUsername;
    @Value("${keycloak.admin.password}")
    private String adminPassword;
    @Value("${keycloak.admin-realm}")
    private String adminRealm;
    private final RestTemplate restTemplate = new RestTemplate();

    public RegisterUserResponse createUser(RegisterUserRequest registerUserRequest){
        String token = getAdminToken();
        String url = serverUrl + "/admin/realms/" + realm + "/users";
        Map<String, Object> user = new HashMap<>();
        user.put("username", registerUserRequest.username());
        user.put("email", registerUserRequest.email());
        user.put("enabled", true);
        user.put("firstName", registerUserRequest.firstName());
        user.put("lastName", registerUserRequest.lastName());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("phone", List.of(registerUserRequest.phone()));
        attributes.put("userType", List.of(registerUserRequest.userType()));
        user.put("attributes", attributes);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Erro ao criar usuário no Keycloak");
        }

        String location = response.getHeaders().getFirst("Location");

        if (location == null) {
            throw new RuntimeException("Keycloak não retornou Location header");
        }

        String userId = location.substring(location.lastIndexOf("/") + 1);

        setPassword(userId, registerUserRequest.password(), token);

        return new RegisterUserResponse(userId, registerUserRequest.firstName(), registerUserRequest.email());

    }
    private void setPassword(String userId, String password, String token) {
        String url = serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        Map<String, Object> cred = new HashMap<>();
        cred.put("type", "password");
        cred.put("value", password);
        cred.put("temporary", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(cred, headers), Void.class);
    }

    private String getAdminToken() {
        String url = serverUrl + "/realms/"+ adminRealm+ "/protocol/openid-connect/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "admin-cli");
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                url, new HttpEntity<>(body, headers), Map.class);

        return (String) response.getBody().get("access_token");
    }

}
