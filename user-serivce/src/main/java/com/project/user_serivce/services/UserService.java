package com.project.user_serivce.services;

import com.project.user_serivce.models.User;
import com.project.user_serivce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserId does not exist"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
