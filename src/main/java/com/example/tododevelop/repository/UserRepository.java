package com.example.tododevelop.repository;

import com.example.tododevelop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findIdByEmailAndPassword (String email, String password);
}
