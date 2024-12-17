package com.example.tododevelop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String userName;
    private String email;
    private String password;
}
