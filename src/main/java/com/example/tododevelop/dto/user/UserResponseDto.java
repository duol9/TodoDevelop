package com.example.tododevelop.dto.user;

import com.example.tododevelop.entity.UserEntity;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponseDto {
    private String userName;
    private String email;
    private LocalDate createAt;

    public UserResponseDto(UserEntity userEntity){
        this.userName = userEntity.getUserName();
        this.email = userEntity.getEmail();
        this.createAt = userEntity.getCreatedAt();
    }

}
