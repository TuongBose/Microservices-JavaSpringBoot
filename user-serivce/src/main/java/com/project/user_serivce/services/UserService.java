package com.project.user_serivce.services;

import com.project.user_serivce.models.User;
import com.project.user_serivce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    @CacheEvict(value = "allUsers" ,allEntries = true)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserId does not exist"));
    }

    //@Cacheable("allUsers") ==> Khi check trong redis se ra dang: 1) "allUsers::SimpleKey []"
    @Override
    @Cacheable(value = "allUsers", key = "'all'")
    public List<User> getAllUsers() {
        System.out.println("Fetching from DB...");
        return userRepository.findAll();
    }

    @Override
    public User ensureUserExistsFromToken(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userRepository.findByKeycloakId(keycloakId)
                .orElseGet(()->{
                    User user = User.builder()
                            .keycloakId(keycloakId)
                            .email(jwt.getClaim("email"))
                            .name(jwt.getClaim("preferred_username"))
                            .build();
                    return userRepository.save(user);
                });
    }

    @Override
    public Optional<User> getUserByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }
}
