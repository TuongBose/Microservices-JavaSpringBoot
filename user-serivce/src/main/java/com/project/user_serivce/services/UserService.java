package com.project.user_serivce.services;

import com.project.user_serivce.models.User;
import com.project.user_serivce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
