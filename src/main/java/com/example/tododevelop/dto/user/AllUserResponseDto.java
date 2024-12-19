package com.example.tododevelop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllUserResponseDto {
    private List<UserResponseDto> allUser;
}
