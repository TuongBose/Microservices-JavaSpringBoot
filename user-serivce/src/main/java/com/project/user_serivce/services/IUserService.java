package com.project.user_serivce.services;

import com.project.user_serivce.models.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    List<User> getAllUsers();
}
