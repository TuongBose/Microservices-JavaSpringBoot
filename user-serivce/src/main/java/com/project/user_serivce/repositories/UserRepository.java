package com.project.user_serivce.repositories;

import com.project.user_serivce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
