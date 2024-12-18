package com.example.tododevelop.dto.user;

import com.example.tododevelop.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllUserResponseDto {
    private List<UserEntity> allUser;
}
