package com.project.user_serivce.controllers;

import com.project.user_serivce.dtos.UserDTO;
import com.project.user_serivce.models.User;
import com.project.user_serivce.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("")
    @CacheEvict(value = "allUsers",allEntries = true)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/keycloak/{sub}")
    public UserDTO getUserByKeycloakId(@PathVariable String sub, @AuthenticationPrincipal Jwt jwt){
        User user = userService.getUserByKeycloakId(sub)
                .orElseGet(()->userService.ensureUserExistsFromToken(jwt));

        return new UserDTO(user.getId(),user.getName(),user.getEmail());
    }
}
