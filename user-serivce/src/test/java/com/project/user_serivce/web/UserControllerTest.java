package com.project.user_serivce.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.user_serivce.controllers.UserController;
import com.project.user_serivce.dtos.UserDTO;
import com.project.user_serivce.models.User;
import com.project.user_serivce.repositories.UserRepository;
import com.project.user_serivce.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestSecurityConfig.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User mockUser;
    private UserDTO mockUserDto;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Manh Tuong");
        mockUser.setEmail("tuong@gmail.com");

        mockUserDto = new UserDTO(1L, "Manh Tuong", "tuong@gmail.com");
    }

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
            return httpSecurity.build();
        }
    }

    @Test
    void getUsers_ShouldReturnListOfUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("tuong@gmail.com"))
                .andDo(print());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manh Tuong"))
                .andExpect(jsonPath("$.email").value("tuong@gmail.com"))
                .andDo(print());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void createUser_ShouldReturnCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manh Tuong"))
                .andExpect(jsonPath("$.email").value("tuong@gmail.com"))
                .andDo(print());


        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void getUserByKeycloakId_UserExists_ShouldReturnUserDTO() throws Exception {
        when(userService.getUserByKeycloakId("haha-123")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/users/keycloak/{sub}", "haha-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manh Tuong"))
                .andExpect(jsonPath("$.email").value("tuong@gmail.com"))
                .andDo(print());


        verify(userService, times(1)).getUserByKeycloakId("haha-123");
        verify(userService, never()).ensureUserExistsFromToken(any());
    }

    @Test
    void getUserByKeycloakId_UserNotExists_ShouldCreateFromToken() throws Exception {
        when(userService.getUserByKeycloakId("haha-123")).thenReturn(Optional.empty());
        when(userService.ensureUserExistsFromToken(any(Jwt.class))).thenReturn(mockUser);

        mockMvc.perform(
                        get("/api/users/keycloak/{sub}", "haha-123")
                                .with(jwt().jwt(jwt ->
                                        jwt.subject("haha-123")
                                                .claim("email", "tuong@gmail.com")
                                                .claim("preferred_username", "Manh Tuong")
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("tuong@gmail.com"))
                .andDo(print());

        verify(userService, times(1)).ensureUserExistsFromToken(any(Jwt.class));
    }
}
