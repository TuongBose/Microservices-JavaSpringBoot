package com.project.user_serivce.unit.services;

import com.project.user_serivce.models.User;
import com.project.user_serivce.repositories.UserRepository;
import com.project.user_serivce.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Manh Tuong");
        mockUser.setEmail("tuong@gmail.com");
        mockUser.setKeycloakId("haha-123");
    }

    @Test
    void testGetAllUsers_ShouldReturnList() {
//        GIVEN phase (Thiet lap du lieu va hanh vi)
        when(userRepository.findAll()).thenReturn(List.of(mockUser));

//        real function
        List<User> result = userService.getAllUsers();

//        THEN phase (Xac nhan dau ra)
        assertEquals(1, result.size());
        assertEquals("Manh Tuong", result.get(0).getName());

//        AND phase (Xac nhan hanh vi)
        verify(userRepository, times(1)).findAll();
    }

    //    testGetUserById_ShouldReturnUser (Cach 1)
    @Test
    void testGetUserById_ShouldReturnUser() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserById(1L);

        assertEquals("Manh Tuong", user.getName());
        assertEquals("tuong@gmail.com", user.getEmail());

        verify(userRepository, times(1)).findById(1L);
    }

//    testGetUserById_ShouldReturnUser(Cach 2)
//    @Test
//    void testGetUserById_ShouldReturnUser() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
//
//        User user = assertDoesNotThrow(() ->
//                userService.getUserById(1L));
//
//        assertEquals("Manh Tuong", user.getName());
//        assertEquals("tuong@gmail.com", user.getEmail());
//
//        verify(userRepository, times(1)).findById(1L);
//    }

    @Test
    void testGetUserById_ShouldThrowIfNotFound() {
        when(userRepository.findById(22L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(22L));

        verify(userRepository, times(1)).findById(22L);
    }

    @Test
    void testEnsureUserExistsFromToken_UserExists() {
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("haha-123");
        when(userRepository.findByKeycloakId("haha-123")).thenReturn(Optional.of(mockUser));

        User user = userService.ensureUserExistsFromToken(jwt);

        assertEquals("Manh Tuong", user.getName());
        assertEquals("tuong@gmail.com", user.getEmail());

        verify(userRepository, times(1)).findByKeycloakId("haha-123");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testEnsureUserExistsFromToken_CreateIfNotExist() {
        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("haha-333");
        when(jwt.getClaim("email")).thenReturn("manhtuongpro@gmail.com");
        when(jwt.getClaim("preferred_username")).thenReturn("user2");
        when(userRepository.findByKeycloakId("haha-333")).thenReturn(Optional.empty());

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                    User user = invocation.getArgument(0, User.class);
                    user.setId(2L);
                    return user;
                }
        );

        User result = userService.ensureUserExistsFromToken(jwt);

        assertEquals("user2", result.getName());
        assertEquals("manhtuongpro@gmail.com", result.getEmail());

        verify(userRepository, times(1)).findByKeycloakId("haha-333");
    }
}
