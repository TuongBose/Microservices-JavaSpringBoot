package com.project.user_serivce.services;

import com.project.user_serivce.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers();
    User ensureUserExistsFromToken(Jwt jwt);
    Optional<User> getUserByKeycloakId(String keycloakId);
}
