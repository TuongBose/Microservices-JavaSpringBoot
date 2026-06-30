package com.project.user_serivce.services;

import com.project.user_serivce.models.User;
import io.jsonwebtoken.Claims;

public interface IJwtService {
    String generateToken (User user);
    Claims extractClaims (String token);
    boolean isTokenValid(String token);
    Long extractUserId(String token);
}
